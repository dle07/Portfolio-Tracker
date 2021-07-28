package com.PortfolioTracker.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import com.PortfolioTracker.JwtUtil;
import com.PortfolioTracker.DAO.AssetsDAO;
import com.PortfolioTracker.DAO.TransactionsDAO;
import com.PortfolioTracker.DAO.UserDAO;
import com.PortfolioTracker.DTOs.StockQuoteDTO;
import com.PortfolioTracker.DTOs.UserAssetsDTO;
import com.PortfolioTracker.Entities.Assets;
import com.PortfolioTracker.Entities.Stock;
import com.PortfolioTracker.Entities.Transaction;
import com.PortfolioTracker.Entities.User;
import com.PortfolioTracker.Pojos.StockPojo;
import com.PortfolioTracker.Pojos.StockValuation;
import com.PortfolioTracker.Util.IexApiUtil;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService{
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private TransactionsDAO transactionsDAO;
  @Autowired
  private AssetsDAO assetsDAO;
  @Autowired
  private IexApiUtil iexApiUtil;

  public ArrayList<Stock> getAllStocksByUsername( final String authorization_header_string ) throws AuthenticationException{   //Extract username from jwt, query list of stocks, return it

    if( authorization_header_string == null || authorization_header_string.startsWith("Bearer ") == false ) throw new AuthenticationException();
    final String jwt_token = authorization_header_string.substring(7);
    final String user_name = jwtUtil.extractUsername(jwt_token);

    return assetsDAO.getAllStocksByUsername(user_name);
  }

  public UserAssetsDTO getUserAssetsDTO( final HttpServletRequest request) throws Exception{
    String authorization_string = request.getHeader("Authorization");
    final String jwt_token = authorization_string.substring(7);
    final String user_name = jwtUtil.extractUsername(jwt_token);
    User current_user = userDAO.getByUsername(user_name);
    Assets user_assets = current_user.getUser_assets();
    List<Stock> user_stocks = user_assets.getStocks();

    try 
    { 
      List<StockValuation> stock_valuations_list = new ArrayList<StockValuation>();
      if(!user_stocks.isEmpty()){
        HashMap<String,StockQuoteDTO> stock_quote_dto_map = iexApiUtil.getStockQuoteDtoBatchRequestByTickers(user_stocks);
        stock_valuations_list = get_stock_valuations_list(user_stocks, stock_quote_dto_map); // List of StockValuation, contains ticker, quantity user has and current market price
      }

      double cash = user_assets.getCash_on_hand();                                                              // How much cash the user has on hand
      double total_stock_valuations = get_stock_valuations(stock_valuations_list);                              // Total valuation of stocks sum
      double total_portfolio_value = cash + total_stock_valuations;                                             // Value of all user assets + cash
    
      UserAssetsDTO result = new UserAssetsDTO(total_portfolio_value, stock_valuations_list, cash, total_portfolio_value );
      return result;
    } catch (Exception e) {
      throw e;
    }
  }

  public UserAssetsDTO getUserAssetsDTO(int user_id) throws Exception{
    
    User current_user = userDAO.getById(user_id);
    Assets user_assets = current_user.getUser_assets();
    List<Stock> user_stocks = user_assets.getStocks();
    try {
      HashMap<String,StockQuoteDTO> stock_quote_dto_map = iexApiUtil.getStockQuoteDtoBatchRequestByTickers(user_stocks);

      List<StockValuation> stock_valuations_list = get_stock_valuations_list(user_stocks, stock_quote_dto_map); // List of StockValuation, contains ticker, quantity user has and current market price
      double cash = user_assets.getCash_on_hand();                                                              // How much cash the user has on hand
      double total_stock_valuations = get_stock_valuations(stock_valuations_list);                              // Total valuation of stocks sum
      double total_portfolio_value = cash + total_stock_valuations;                                             // Value of all user assets + cash
    
      UserAssetsDTO result = new UserAssetsDTO(total_portfolio_value, stock_valuations_list, cash, total_portfolio_value );
      return result;
    } catch (Exception e) {
      throw e;
    }
  }

  /*
    Check if user even has the stock and enough shares to sell
    Update the cash
    Update the stock in the db, if it's 0, then delete it completely
  */
  @Override
  public void sellStock(JsonNode body, HttpServletRequest request) throws Exception {
    String ticker = body.get("ticker").asText().toUpperCase();
    Integer quantity = body.get("quantity").asInt();
    String user_name = jwtUtil.extractUsername( request.getHeader("Authorization").substring(7) );
    int assets_id= userDAO.get_assets_id(user_name);

    if( !assetsDAO.user_owns(user_name, ticker) || assetsDAO.getQuantityCount(assets_id, ticker) < quantity){
      throw new Exception("User doesn't own stock or has enough quantity");
    }
    StockQuoteDTO stockQuoteDTO = iexApiUtil.getStockInfoDTO(ticker);

    double cash = quantity * stockQuoteDTO.getLatestPrice();
    assetsDAO.addToUserCash(assets_id, cash);
    assetsDAO.decrementStockQuantity(assets_id, ticker, quantity);
    

  }

  public void purchaseStock(JsonNode body, HttpServletRequest request) throws Exception{   //Checks to see if the user can buy the stock
    String jwt_token = request.getHeader("Authorization").substring(7);
    String user_name = jwtUtil.extractUsername(jwt_token);
    User user = userDAO.getByUsername(user_name);
    Assets user_assets = user.getUser_assets();
    double cash = user_assets.getCash_on_hand();
    int quantity = body.get("quantity").asInt();
    String ticker = body.get("ticker").asText().toUpperCase();

    StockQuoteDTO quote_information = iexApiUtil.getStockInfoDTO(ticker);
    double total_price = quote_information.getClose() * quantity;

    if( cash < total_price )throw new Exception("User doesn't have sufficent funds for purchase");  // If user doesn't have sufficent funds, stop;
    // At this point user purchase the stock, we have to update user's cash and insert the stock into user's assets
    Stock new_stock = new Stock(ticker,quantity);
    user_assets.setCash_on_hand(cash - total_price);
    assetsDAO.updateAssets(user_assets);
    assetsDAO.insert_stock(user_name, new StockPojo(ticker,quantity));      // Record transaction here
    Transaction new_transaction = new Transaction(ticker, "BUY", quantity, quote_information.getClose(), new Date());
    new_transaction.setAssets(user_assets);
    transactionsDAO.insert(new_transaction);

  }

  public double get_stock_valuations (List<StockValuation> stock_valuations_list){
    double result = 0;
    for( StockValuation current_valuation : stock_valuations_list){
      result += current_valuation.getTotal_valuation();
    }
    return result;
  }

  public List<StockValuation> get_stock_valuations_list(List<Stock> user_stocks, HashMap<String,StockQuoteDTO> stock_quote_dto_map){   // user_stocks contains list of all Stock that has information about ticker and quantity
    List result = new ArrayList<StockValuation>();
    System.out.println(stock_quote_dto_map);
    for( Stock stock : user_stocks){
      StockValuation stock_valuation = new StockValuation();
      StockQuoteDTO current_stock_quote_dto = stock_quote_dto_map.get(stock.getTicker());
      System.out.println( current_stock_quote_dto);
      double total_valuation = stock.getQuantity() * current_stock_quote_dto.getClose();


      stock_valuation.setTicker(stock.getTicker());
      stock_valuation.setQuantity(stock.getQuantity());
      stock_valuation.setTotal_valuation(total_valuation);

      result.add(stock_valuation);
    }

    return result;
  }




  


}
