package com.PortfolioTracker.DTOs;

public class StockQuoteDTO {
  private double close;
  private double high;
  private double open;
  private String symbol;

  public StockQuoteDTO(){

  }

  public StockQuoteDTO(double close, double high, double open, String symbol) {
    this.close = close;
    this.high = high;
    this.open = open;
    this.symbol = symbol;
  }

  public double getClose() {
    return close;
  }
  public void setClose(double close) {
    this.close = close;
  }
  public double getHigh() {
    return high;
  }
  public void setHigh(double high) {
    this.high = high;
  }
  public double getOpen() {
    return open;
  }
  public void setOpen(double open) {
    this.open = open;
  }
  public String getSymbol() {
    return symbol;
  }
  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }




  @Override
  public String toString() {
    return "StockQuoteDTO [close=" + close + ", high=" + high + ", open=" + open + ", symbol=" + symbol + "]";
  }


  
}
