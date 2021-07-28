package com.PortfolioTracker.DTOs;

import java.util.List;

import com.PortfolioTracker.Pojos.StockValuation;

public class UserAssetsDTO {
  
  private double total_portfolio_value;  // cash + assets_value
  private List<StockValuation> all_stocks_and_valuations;  // All the stocks the user owns, and their current valuation
  private double cash;
  private double assets_value;      // how much 



  public UserAssetsDTO(){

  }

  

  public UserAssetsDTO(double total_portfolio_value, List<StockValuation> all_stocks_and_valuations, double cash,
      double assets_value) {
    this.total_portfolio_value = total_portfolio_value;
    this.all_stocks_and_valuations = all_stocks_and_valuations;
    this.cash = cash;
    this.assets_value = assets_value;
  }



  public double getTotal_portfolio_value() {
    return total_portfolio_value;
  }



  public void setTotal_portfolio_value(double total_portfolio_value) {
    this.total_portfolio_value = total_portfolio_value;
  }



  public double getCash() {
    return cash;
  }



  public void setCash(double cash) {
    this.cash = cash;
  }



  public double getAssets_value() {
    return assets_value;
  }



  public void setAssets_value(double assets_value) {
    this.assets_value = assets_value;
  }



  public List<StockValuation> getAll_stocks_and_valuations() {
    return all_stocks_and_valuations;
  }



  public void setAll_stocks_and_valuations(List<StockValuation> all_stocks_and_valuations) {
    this.all_stocks_and_valuations = all_stocks_and_valuations;
  }

  


  
}
