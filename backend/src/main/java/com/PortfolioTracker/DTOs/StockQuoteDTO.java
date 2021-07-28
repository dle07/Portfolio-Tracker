package com.PortfolioTracker.DTOs;

public class StockQuoteDTO {
  private double close;
  private double high;
  private double latestPrice;
  private double previousClose;
  private double open;
  private String symbol;

  public StockQuoteDTO(){

  }

  public StockQuoteDTO(double close, double latestPrice, 
  double previousClose, double high, double open, String symbol) {
    this.close = close;
    this.high = high;
    this.latestPrice = latestPrice;
    this.previousClose = previousClose;
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

  public double getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(double latestPrice) {
    this.latestPrice = latestPrice;
  }

  public double getPreviousClose() {
    return previousClose;
  }

  public void setPreviousClose(double previousClose) {
    this.previousClose = previousClose;
  }

  @Override
  public String toString() {
    return "StockQuoteDTO [close=" + close + ", high=" + high + ", open=" + open + ", symbol=" + symbol + "]";
  }


  
}
