package com.PortfolioTracker.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.PortfolioTracker.DAO.UserDAO;
import com.PortfolioTracker.DTOs.UserRegistrationDTO;
import com.PortfolioTracker.Entities.Stock;
import com.PortfolioTracker.Services.StockService;
import com.PortfolioTracker.Services.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@CrossOrigin
public class UserController {

  @Autowired
  private UserService userService;
  @Autowired 
  private StockService stockService;

  
  
  @PostMapping(value = "/user/register")
  public ResponseEntity<?> registerNewUser(@RequestBody UserRegistrationDTO newUserDetails){
    
    try {
      userService.registerUser(newUserDetails);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<String>("Username Taken", HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }


  @PostMapping( value = "/test/1")
  public void test1(@RequestBody String data){
    System.out.print(data);

    


    
  }

  @GetMapping(value = "/test")
  public ResponseEntity<Map<String,String>> test (){

    
    HashMap result = new HashMap<String,String>();
    result.put("one","two");
    result.put("one","two");
    result.put("one","two");
    result.put("one","two");

    return new ResponseEntity<> (result, HttpStatus.OK);
  }

  @GetMapping(value = "/user/getAllStocks")
  public ResponseEntity<?> getAllStocksByUsername(HttpServletRequest request){
    try {
      final String authorization_header_string = request.getHeader("Authorization");
      ArrayList<Stock> userStocks = stockService.getAllStocksByUsername(authorization_header_string);
      return new ResponseEntity<>( userStocks,HttpStatus.OK);
    
    } catch (Exception e) {
      return new ResponseEntity<String>("No Authorization Header Provided",HttpStatus.BAD_REQUEST);
    }

  }
}
