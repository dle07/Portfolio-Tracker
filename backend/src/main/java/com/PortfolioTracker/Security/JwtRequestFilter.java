package com.PortfolioTracker.Security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.PortfolioTracker.JwtUtil;
import com.PortfolioTracker.DAO.UserDAO;
import com.PortfolioTracker.Services.ApplicationUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//This filter intercepts every request once and examines the header
@Component
public class JwtRequestFilter extends  OncePerRequestFilter{
    
    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;
    
    @Autowired
    private UserDAO userDAO;  // The jwtUtil validate function needs a UserDetails to valiadte, this provides it
    @Autowired
    private JwtUtil jwtUtil;   //We need this to verify that the JWT is valid

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  //Filter chain, has the option of passing on to next filter
            throws ServletException, IOException {
        
                //Begin extracting information from the token
        final String authorization_header = request.getHeader("Authorization");
        String username = null;
        String jwt_token = null;

        if( authorization_header != null && authorization_header.startsWith("Bearer ")){
            jwt_token = authorization_header.substring(7);  //Extract token, leave out "Bearer " substring part
            username = jwtUtil.extractUsername(jwt_token);
        }
        if( username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(username);
            if( jwtUtil.validateToken(jwt_token, userDetails)){  //If token if sucessful


                //Code below is what Spring Security does by default, but since we're overriding a filter, we need to add it along
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        filterChain.doFilter(request,response);
        
    }
    
}
