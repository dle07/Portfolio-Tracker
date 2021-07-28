package com.PortfolioTracker.Util;

import java.util.HashMap;
import java.util.List;

import com.PortfolioTracker.DTOs.StockQuoteDTO;
import com.PortfolioTracker.Entities.Stock;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

// Key = "pk_e3cedf2925bd4884b2951f48b7e413e0"
// All request url endpoints end with ?token=pk_e3cedf2925bd4884b2951f48b7e413e0 
//Ex: https://cloud.iexapis.com/stable/tops?token=YOUR_TOKEN_HERE&symbols=aapl


/*
  Make API calls with WebClient 
    1)create an instance
    2)make a request
    3)handle the response

*/
@Service
public class IexApiUtil {
   
  @Autowired
  private ObjectMapper objectMapper;

  final private String key = "token=pk_e3cedf2925bd4884b2951f48b7e413e0";
  final private String start_url = "https://cloud.iexapis.com/v1/";

//https://cloud.iexapis.com/v1/stock/market/batch?symbols=aapl,fb,tsla&types=quote&token=pk_e3cedf2925bd4884b2951f48b7e413e0
  public StockQuoteDTO getStockInfoDTO( String ticker) throws Exception{

    try {
      String uri_request = start_url + "stock/market/batch?symbols="+ticker+"&types=quote&"+key;

      WebClient.ResponseSpec response = WebClient.create()   // build a WebClient instance
            .get().uri(uri_request).accept(MediaType.APPLICATION_JSON).retrieve();   // call .retrieve to execute the call and get a ResponseSpec

      JsonNode body = response.bodyToMono(JsonNode.class).block();
      JsonNode current_quote = body.get(ticker.toUpperCase()).get("quote");
      StockQuoteDTO result = new StockQuoteDTO(
        
                                              current_quote.get("close").asDouble(), 
                                              current_quote.get("latestPrice").asDouble(),
                                              current_quote.get("previousClose").asDouble(),
                                              current_quote.get("high").asDouble(), 
                                              current_quote.get("open").asDouble(), 
                                              current_quote.get("symbol").asText());
      System.out.println(result);
      return result;

    } catch (Exception e) {
      throw e;
    }
  }
  public HashMap<String,StockQuoteDTO> getStockQuoteDtoBatchRequestByTickers(List<Stock> user_stocks) throws Exception{

    HashMap<String,StockQuoteDTO> result = new HashMap<String, StockQuoteDTO>();                  // Maps ticker String to it's respective stock quote information
    if( user_stocks.isEmpty()) throw new Exception("Requested Ticker Query List is empty");
    String symbols = get_batch_symbol_uri_component(user_stocks);

    String uri_request = start_url + "stock/market/batch?symbols="+symbols+"&types=quote&"+key;
    WebClient.ResponseSpec response = WebClient.create()
              .get().uri(uri_request).retrieve();
    
    JsonNode body = response.bodyToMono(JsonNode.class).block();
    for( Stock stock : user_stocks){
      JsonNode current_quote = body.get(stock.getTicker().toUpperCase()).get("quote");
      System.out.println("Printing current Quote Now ==============================================");
      System.out.println(current_quote);
      result.put(stock.getTicker(), new StockQuoteDTO(
                                      current_quote.get("close").asDouble(),
                                      current_quote.get("latestPrice").asDouble(),
                                      current_quote.get("previousClose").asDouble(),
                                      current_quote.get("high").asDouble(),
                                      current_quote.get("open").asDouble(),
                                      current_quote.get("symbol").asText()
                                      ));
    }                                    

    return result;
    
  }


  private  String get_batch_symbol_uri_component( List<Stock> user_stocks ){
    StringBuilder result_builder = new StringBuilder();
    for( Stock stock : user_stocks){
      result_builder.append(stock.getTicker()+",");
    }
    if( result_builder.length() > 0){
      result_builder.deleteCharAt(result_builder.length()-1);
    }
    return result_builder.toString();
  }

}
