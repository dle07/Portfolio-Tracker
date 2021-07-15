package com.PortfolioTracker.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.web.bind.annotation.CookieValue;

@Entity
public class Assets {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private int id;

  @OneToMany(fetch = FetchType.LAZY , cascade=CascadeType.ALL)
  private List<Stock> stocks = new ArrayList<Stock>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Transaction> transactions = new ArrayList<Transaction>();

  @OneToOne (mappedBy = "user_assets")
  private User user;
  
  public Assets(){

  }


  public int getId() {
    return id;
  }


  public void setId(int id) {
    this.id = id;
  }


  public List<Stock> getStocks() {
    return stocks;
  }


  public void setStocks(List<Stock> stocks) {
    this.stocks = stocks;
  }



  
  
}
