package com.PortfolioTracker.Controller;

import com.PortfolioTracker.JwtUtil;

import com.PortfolioTracker.Security.AuthenticationRequest;
import com.PortfolioTracker.Security.AuthenticationResponse;
import com.PortfolioTracker.Services.ApplicationUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;
    
    @PostMapping(value = "/user/authenticate")  // We need to authenticate
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{

        try {
            authManager.authenticate(   // Throws an exception if authenticaiton fails
            new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("INVALID CREDENTIALS", HttpStatus.BAD_REQUEST);
        }
        //By this point authentication has suceeded, we'll create and return a JWT token
        final UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        // Pass in UserDetails
        final String jwt_token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt_token));

    }



}
