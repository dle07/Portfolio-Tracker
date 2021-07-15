package com.PortfolioTracker.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Stock {

  @Id
  @GeneratedValue(strategy =GenerationType.AUTO)
  private String id;
  
  private String ticker;
  private int quantity;         // Can only have whole shares of a stock
  

  public Stock(){

  }

  

  public String getId() {
    return id;
  }



  public void setId(String id) {
    this.id = id;
  }



  public String getTicker() {
    return ticker;
  }



  public void setTicker(String ticker) {
    this.ticker = ticker;
  }



  public int getQuantity() {
    return quantity;
  }



  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }



  @Override
  public String toString() {
    return "Stock [id=" + id + ", quantity=" + quantity + ", ticker=" + ticker + " ]";
  }








  
  
}

