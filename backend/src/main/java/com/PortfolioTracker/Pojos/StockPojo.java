package com.PortfolioTracker.Pojos;

public class StockPojo {

  private String ticker;
  private int quantity;

  public StockPojo(){

  }
  public StockPojo(String ticker, int quantity) {
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
  
  @Override
  public String toString() {
    return "StockPojo [quantity=" + quantity + ", ticker=" + ticker + "]";
  }


  
}
