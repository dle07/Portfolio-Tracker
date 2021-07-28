package com.PortfolioTracker.Util;

import com.PortfolioTracker.JwtUtil;
import com.PortfolioTracker.Security.AuthenticationRequest;
import com.PortfolioTracker.Services.ApplicationUserDetailsService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticateUtil {
  
  @Autowired
  private AuthenticationManager authManager;
  @Autowired
  private ApplicationUserDetailsService applicationUserDetailsService;
  @Autowired 
  private JwtUtil jwtUtil;

  public String authenticateAndGenerateToken(AuthenticationRequest authenticationRequest) throws BadCredentialsException
  {
    try {
      authManager.authenticate(   // Throws an exception if authenticaiton fails
      new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
      final UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
      final String jwt_token = jwtUtil.generateToken(userDetails);
  
      return jwt_token;
    } catch (BadCredentialsException e) {
        throw e;
    }


  }



}
