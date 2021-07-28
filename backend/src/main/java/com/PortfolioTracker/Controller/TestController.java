package com.PortfolioTracker.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;


@Controller
public class TestController {

  
  @PostMapping(value = "/test/1")
  public ResponseEntity<?> purchase_stock(@RequestBody JsonNode objectNode){

    System.out.println(objectNode);
    System.out.println(objectNode.get("username"));
    System.out.println(objectNode.get("object"));
    System.out.println(objectNode.get("object").get("1"));

    return new ResponseEntity("All right", HttpStatus.OK);
  }

  @GetMapping(value = "/test")
  public ResponseEntity<Map<String,String>> test (){

    
    HashMap result = new HashMap<String,String>();
    result.put("one","two");
    result.put("one","two");
    result.put("one","two");
    result.put("one","two");

    return new ResponseEntity<> (result, HttpStatus.OK);
  }
}
