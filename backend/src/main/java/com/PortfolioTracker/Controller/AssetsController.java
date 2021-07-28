package com.PortfolioTracker.Controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.PortfolioTracker.JwtUtil;
import com.PortfolioTracker.DTOs.UserAssetsDTO;
import com.PortfolioTracker.Entities.Stock;
import com.PortfolioTracker.Services.StockServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/assets")
public class AssetsController {
  @Autowired
  private StockServiceImpl stockService;
  @Autowired
  private JwtUtil jwtUtil;


  
  @PostMapping(value = "/purchase_stock")
  public ResponseEntity<?> purchaseStock(@RequestBody JsonNode json_body, HttpServletRequest request){
    try {



      stockService.purchaseStock(json_body, request);
      UserAssetsDTO userAssetsDTO = stockService.getUserAssetsDTO(request);
      
      return new ResponseEntity<UserAssetsDTO>(userAssetsDTO,HttpStatus.OK);
      
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<Exception>(e, HttpStatus.BAD_REQUEST);
    }

  }
  @GetMapping(value = "/get_assets_summary")
  public ResponseEntity<?> getAllStocksByUsername(HttpServletRequest request){
    try {
      UserAssetsDTO userAssetsDTO = stockService.getUserAssetsDTO(request);
      return new ResponseEntity<UserAssetsDTO>( userAssetsDTO,HttpStatus.OK);
    
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<Exception>(e,HttpStatus.BAD_REQUEST);
    }

  }

  @PostMapping(value = "/sell_stock")
  public ResponseEntity<?> sellStock(@RequestBody JsonNode json_body, HttpServletRequest request){
    try {

      stockService.sellStock(json_body, request);
      UserAssetsDTO userAssetsDTO = stockService.getUserAssetsDTO(request);
      return new ResponseEntity<UserAssetsDTO>(userAssetsDTO,HttpStatus.OK);
      
    } catch (Exception e) {
        e.printStackTrace();
      System.out.println(e);
      return new ResponseEntity<Exception>(e, HttpStatus.BAD_REQUEST);
    }

  }

  
}
