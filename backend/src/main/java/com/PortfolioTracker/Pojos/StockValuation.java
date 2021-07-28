package com.PortfolioTracker.Pojos;

public class StockValuation {

  private String ticker;
  private int quantity;
  private double total_valuation;    // quantity * current_market_value_price;


  public StockValuation(){

  }


  public StockValuation(String ticker, int quantity, double total_valuation) {
    this.ticker = ticker.toUpperCase();
    this.quantity = quantity;
    this.total_valuation = total_valuation;
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


  public double getTotal_valuation() {
    return total_valuation;
  }


  public void setTotal_valuation(double total_valuation) {
    this.total_valuation = total_valuation;
  }


  @Override
  public String toString() {
    return "StockValuation [quantity=" + quantity + ", ticker=" + ticker + ", total_valuation=" + total_valuation + "]";
  }




  

  

}
