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

  @OneToMany(fetch = FetchType.LAZY , cascade=CascadeType.ALL, mappedBy = "assets")
  private List<Stock> stocks = new ArrayList<Stock>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "assets")
  private List<Transaction> transactions = new ArrayList<Transaction>();

  @OneToOne (mappedBy = "user_assets")
  private User user;
  @Column(scale = 2)
  private double cash_on_hand = 5000;

 
  
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


  public List<Transaction> getTransactions() {
    return transactions;
  }


  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }


  public User getUser() {
    return user;
  }


  public void setUser(User user) {
    this.user = user;
  }


  public double getCash_on_hand() {
    return cash_on_hand;
  }


  public void setCash_on_hand(double cash_on_hand) {
    this.cash_on_hand = cash_on_hand;
  }

  


  
  
}
