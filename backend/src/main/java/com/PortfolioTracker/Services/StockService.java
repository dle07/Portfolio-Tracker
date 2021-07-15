package com.PortfolioTracker.Services;

import java.util.ArrayList;

import javax.naming.AuthenticationException;


import com.PortfolioTracker.DAO.AssetsDAO;
import com.PortfolioTracker.DAO.UserDAO;
import com.PortfolioTracker.Entities.Stock;
import com.PortfolioTracker.Util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
  
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private AssetsDAO assetsDAO;

  public ArrayList<Stock> getAllStocksByUsername( final String authorization_header_string ) throws AuthenticationException{   //Extract username from jwt, query list of stocks, return it

    
    if( authorization_header_string == null || authorization_header_string.startsWith("Bearer ") == false ) throw new AuthenticationException();

    final String jwt_token = authorization_header_string.substring(7);

    final String user_name = jwtUtil.extractUsername(jwt_token);

      
    return assetsDAO.getAllStocksByUsername(user_name);
  }


}
