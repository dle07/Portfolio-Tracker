package com.PortfolioTracker.Services;

import java.util.ArrayList;

import com.PortfolioTracker.DAO.TransactionsDAO;
import com.PortfolioTracker.DTOs.TransactionDTO;
import com.PortfolioTracker.Entities.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {
  

  @Autowired
  private TransactionsDAO transactionsDAO;
  

  public ArrayList<TransactionDTO> getAllPastTransactions(final String user_name){
    return transactionsDAO.getAllPastTransactions(user_name);
  }


  
}
