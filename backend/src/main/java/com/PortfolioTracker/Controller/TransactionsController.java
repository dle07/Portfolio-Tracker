package com.PortfolioTracker.Controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.PortfolioTracker.JwtUtil;
import com.PortfolioTracker.DTOs.TransactionDTO;
import com.PortfolioTracker.Entities.Transaction;
import com.PortfolioTracker.Services.TransactionsService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@CrossOrigin
public class TransactionsController {
  
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired 
  private TransactionsService transactionsService;

  @GetMapping("/all")
  public ResponseEntity<?> getAllPastTransactions(HttpServletRequest request){
    try {
      final String jwt_token = request.getHeader("Authorization").substring(7);
      final String user_name = jwtUtil.extractUsername(jwt_token);
      ArrayList<TransactionDTO> result = transactionsService.getAllPastTransactions(user_name);
      return new ResponseEntity<ArrayList<TransactionDTO>> ( result, HttpStatus.OK);

    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<Exception>(e, HttpStatus.BAD_REQUEST);
    }


  }
}
