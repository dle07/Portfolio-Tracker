package com.PortfolioTracker.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import com.PortfolioTracker.DTOs.StockQuoteDTO;
import com.PortfolioTracker.DTOs.UserAssetsDTO;
import com.PortfolioTracker.Entities.Stock;
import com.PortfolioTracker.Pojos.StockValuation;
import com.fasterxml.jackson.databind.JsonNode;

public interface StockService {
  public ArrayList<Stock> getAllStocksByUsername( final String authorization_header_string )throws AuthenticationException;

  public UserAssetsDTO getUserAssetsDTO( final HttpServletRequest request) throws Exception;
  public UserAssetsDTO getUserAssetsDTO(int user_id) throws Exception;


  public double get_stock_valuations (List<StockValuation> stock_valuations_list);
  public List<StockValuation> get_stock_valuations_list(List<Stock> user_stocks, HashMap<String,StockQuoteDTO> stock_quote_dto_map);

  public void purchaseStock(JsonNode body, HttpServletRequest request) throws Exception;
  public void sellStock( JsonNode body, HttpServletRequest request) throws Exception;
}
