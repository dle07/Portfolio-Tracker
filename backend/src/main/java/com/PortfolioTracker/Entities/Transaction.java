package com.PortfolioTracker.Entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String ticker;
  private String transaction_type;
  private int quantity;
  private double value_at_transaction; // How much the stock was valued at at the time of the transaction.
  private Date date;

  


  public Transaction(){

  }

  

  public Transaction( String ticker, String transaction_type, int quantity, double value_at_transaction,
      Date date) {
    this.ticker = ticker;
    this.transaction_type = transaction_type;
    this.quantity = quantity;
    this.value_at_transaction = value_at_transaction;
    this.date = date;
  }



  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
  


}
