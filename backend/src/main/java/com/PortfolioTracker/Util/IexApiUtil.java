package com.PortfolioTracker.Util;

import java.util.List;

import com.PortfolioTracker.DTOs.StockQuoteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
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

  public StockQuoteDTO getStockInfoDTO( String ticker){

    try {
    String uri_request = start_url+"stock/" + ticker + "/previous?" + key;

    WebClient.ResponseSpec response = WebClient.create()   // build a WebClient instance
          .get().uri(uri_request).retrieve();   // call .retrieve to execute the call and get a ResponseSpec

    String response_body_string = response.bodyToMono(String.class).block();
    
    StockQuoteDTO result = objectMapper.readValue(response_body_string, StockQuoteDTO.class);
    return result;
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;

  }
  public List<StockQuoteDTO> getStockQuoteDtoBatchRequestByTickers(List<String> tickers) throws Exception{
    if( tickers.isEmpty()) throw new Exception("Requested Ticker Query List is empty");
    String symbols = String.join(",", tickers);

    String uri_request = start_url + "stock/market/batch?symbols="+symbols+"&types=quote&"+key;
    WebClient.ResponseSpec response = WebClient.create()
              .get().uri(uri_request).retrieve();
    
    String response_body_json_string = response.bodyToMono(String.class).block();
    System.out.println(response_body_json_string);

    
    //ArrayList stock_dto_batch_list_result = objectMapper.readValue(response_body_json_string)
    return null;
    
  }


}
