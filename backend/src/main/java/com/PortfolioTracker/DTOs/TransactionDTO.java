package com.PortfolioTracker.DTOs;

import java.util.Date;

import com.PortfolioTracker.Entities.Transaction;

public class TransactionDTO {
  
  private String ticker;
  private String transaction_type;
  private int quantity;
  private double value_at_transaction;
  private double price_per;
  private String transaction_date;

  public TransactionDTO(){

  }
  public TransactionDTO (Transaction transaction){

    this.ticker  = transaction.getTicker();
    this.transaction_type = transaction.getTransaction_type();
    this.quantity = transaction.getQuantity();
    this.value_at_transaction = transaction.getValue_at_transaction();
    this.price_per = this.value_at_transaction/this.quantity;
    this.transaction_date = transaction.getDate().toString();
  }



  public double getPrice_per() {
    return price_per;
  }
  public void setPrice_per(double price_per) {
    this.price_per = price_per;
  }
  public String getTicker() {
    return ticker;
  }
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }
  public String getTransaction_type() {
    return transaction_type;
  }
  public void setTransaction_type(String transaction_type) {
    this.transaction_type = transaction_type;
  }
  public int getQuantity() {
    return quantity;
  }
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  public double getValue_at_transaction() {
    return value_at_transaction;
  }
  public void setValue_at_transaction(double value_at_transaction) {
    this.value_at_transaction = value_at_transaction;
  }
  public String getTransaction_date() {
    return transaction_date;
  }
  public void setTransaction_date(String transaction_date) {
    this.transaction_date = transaction_date;
  }

  
  
} 
