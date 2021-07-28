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
  private int id;
  
  private String ticker;
  private int quantity;         // Can only have whole shares of a stock
  
  @ManyToOne
  @JoinColumn(name = "assets_id")
  private Assets assets;

  public Stock(){

  }

  

  public Stock(String ticker, int quantity) {
    this.ticker = ticker.toUpperCase();
    this.quantity = quantity;
  }



  public String getTicker() {
    return ticker;
  }


  public void setTicker(String ticker) {
    this.ticker = ticker.toUpperCase();
  }


  public int getQuantity() {
    return quantity;
  }



  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  
  public Assets getAssets() {
    return assets;
  }



  public void setAssets(Assets assets) {
    this.assets = assets;
  }



  public int getId() {
    return id;
  }



  public void setId(int id) {
    this.id = id;
  }



  @Override
  public String toString() {
    return "Stock [id=" + id + ", quantity=" + quantity + ", ticker=" + ticker + " ]";
  }








  
  
}

