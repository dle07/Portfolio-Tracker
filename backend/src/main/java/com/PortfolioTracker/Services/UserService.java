package com.PortfolioTracker.Services;

import com.PortfolioTracker.Controller.AuthenticationController;
import com.PortfolioTracker.Controller.UserController;
import com.PortfolioTracker.DAO.UserDAO;
import com.PortfolioTracker.DTOs.UserRegistrationDTO;
import com.PortfolioTracker.Security.AuthenticationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  @Autowired
  private AuthenticationController authController;


  public ResponseEntity<?> registerUser(UserRegistrationDTO newUser) {   // Returns a JWT Token
    final String user_name = newUser.getUsername();
    final String password = newUser.getPassword();
    final String encodedPassword = passwordEncoder.encode(password);
    System.out.println("user  " + newUser.getUsername() + " is being called and ddddddddddddddddddd");
    System.out.println(encodedPassword);

    if(userDAO.userNameTaken(user_name) ){
      return new ResponseEntity<String>("Username already taken", HttpStatus.BAD_REQUEST);
    }
    userDAO.addUser(user_name, encodedPassword);
      
    try {
      return authController.authenticate(new AuthenticationRequest(user_name, password));
    } catch (Exception e) {
    
      return new ResponseEntity<Exception>(e, HttpStatus.BAD_REQUEST);
    }


  }
}
