package com.PortfolioTracker.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




import com.PortfolioTracker.DTOs.UserRegistrationDTO;

import com.PortfolioTracker.Services.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;
  @CrossOrigin
  @PostMapping(value = "/register")
  public ResponseEntity<?> registerNewUser(@RequestBody UserRegistrationDTO newUserDetails){
    
    return userService.registerUser(newUserDetails);

  }


}
