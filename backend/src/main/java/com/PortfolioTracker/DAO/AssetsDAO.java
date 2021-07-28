package com.PortfolioTracker.DAO;

import java.math.BigInteger;
import java.util.ArrayList;


import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.transaction.Transactional;


import com.PortfolioTracker.Entities.Assets;
import com.PortfolioTracker.Entities.Stock;
import com.PortfolioTracker.Entities.User;
import com.PortfolioTracker.Pojos.StockPojo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import ch.qos.logback.core.net.SyslogOutputStream;

@Repository
@Transactional
public class AssetsDAO {

  @Autowired
  private UserDAO userDAO;
  @Autowired
  private EntityManager em;
  
  @OneToMany(cascade = CascadeType.ALL)
  private ArrayList<Stock> stocks = new ArrayList<Stock>();

  @SuppressWarnings("unchecked")
  public ArrayList<Stock> getAllStocksByUsername( final String user_name ){
    int assets_id = userDAO.get_assets_id(user_name);
    Query query = em.createNativeQuery("select x from stock x where x.assets_id = :assets_id")
                    .setParameter("assets_id", assets_id);
    ArrayList<Stock> result = new ArrayList<Stock>( query.getResultList() );

    return result;
  }

  public void updateAssets(Assets assets){
    em.persist(assets);
  }

  public boolean user_owns( String username, String ticker){
    int assets_id = userDAO.get_assets_id(username);

    Query query = em.createNativeQuery("select count(x) from stock x where x.assets_id = :assets_id and x.ticker = :ticker")
                    .setParameter("assets_id", assets_id)
                    .setParameter("ticker", ticker.toUpperCase());
    BigInteger count = (BigInteger) query.getSingleResult();
    return( count.equals(BigInteger.valueOf(0) )? false: true);
    

  }

  public boolean user_owns( int assets_id, String ticker){
    Query query = em.createNativeQuery("select count(x) from stock x where x.assets_id = :assets_id and x.ticker = :ticker")
                    .setParameter("assets_id", assets_id)
                    .setParameter("ticker", ticker.toUpperCase());
  BigInteger count = (BigInteger) query.getSingleResult();
  return( count.equals(BigInteger.valueOf(0))? false: true);
}
  public double getUserCash(int assets_id){
    Query query = em.createNativeQuery("select x.cash_on_hand from assets x where x.id =:assets_id")
                                      .setParameter("assets_id", assets_id);
    Double result = 0D;
    result = (Double)query.getSingleResult();
    return result;
  }
  //When adding a stock, there are two cases 1) User already owns the stock. We just need to add on the number. 2) User doesn't own the stock. We need to completely persist a new instance
  public void insert_stock( String username, StockPojo stockPojo){

    int assets_id = userDAO.get_assets_id(username);
      // Case 1)
    if( user_owns(assets_id, stockPojo.getTicker())){
        // Query to get the quantity 
        Query get_quantity_query = em.createNativeQuery("select x.quantity from stock x where x.assets_id = :assets_id and x.ticker = :ticker")
                        .setParameter("assets_id", assets_id)
                        .setParameter("ticker", stockPojo.getTicker());
        int quantity = (Integer)get_quantity_query.getSingleResult();

        Query update_quantity_query = em.createNativeQuery("update stock x set quantity = :new_quantity where x.ticker = :ticker and x.assets_id = :assets_id")
                            .setParameter("new_quantity", quantity + stockPojo.getQuantity())
                            .setParameter("ticker", stockPojo.getTicker())
                            .setParameter("assets_id", assets_id);
        update_quantity_query.executeUpdate();
        
    }else{
      // Case 2)
      User user = userDAO.getByUsername(username);
      Assets users_assets = user.getUser_assets();
      Stock new_stock = new Stock(stockPojo.getTicker(),stockPojo.getQuantity());
      new_stock.setAssets(users_assets);
      em.persist(new_stock);
    }
  }
  
  public int getQuantityCount(String user_name, String ticker){
    int assets_id = userDAO.get_assets_id(user_name);
    Query query = em.createNativeQuery("select x.quantity from stock x where x.assets_id = :assets_id and x.ticker = :ticker")
                    .setParameter("assets_id", assets_id)
                    .setParameter("ticker", ticker);
    Integer result = (Integer)query.getSingleResult();

    return result;
  }
  public int getQuantityCount(int assets_id, String ticker){
    Query query = em.createNativeQuery("select x.quantity from stock x where x.assets_id = :assets_id and x.ticker = :ticker")
                    .setParameter("assets_id", assets_id)
                    .setParameter("ticker", ticker);
    Integer result = (Integer)query.getSingleResult();

    return result;
  }

  public void addToUserCash( int assets_id, double cash){
    double user_cash = getUserCash(assets_id);
    user_cash +=cash;
    Query query = em.createNativeQuery("update assets set cash_on_hand = :user_cash where id = :assets_id ")
                    .setParameter("user_cash", user_cash)
                    .setParameter("assets_id", assets_id);
    query.executeUpdate();

  }

  public void decrementStockQuantity(int assets_id, String ticker, int quantity){
    
    int current_quantity = getQuantityCount(assets_id, ticker);
    if( quantity == current_quantity )
    {
      deleteStock(assets_id, ticker);
      return;
    }
    else
    {
      current_quantity -= quantity;
      System.out.print("printing out curent ssssssssssssssssssssssss");
      System.out.print(current_quantity);
      //                                update stock set quantity = 500 where assets_id = 2 and ticker = 'TSM';
      Query query = em.createNativeQuery("update stock set quantity = :updated_quantity where assets_id = :assets_id and ticker = :ticker")
      .setParameter("updated_quantity", current_quantity)
      .setParameter("assets_id", assets_id)
      .setParameter("ticker", ticker); 
      query.executeUpdate();
    }
    
  }

  public void deleteStock(int assets_id, String ticker){
    Query query = em.createNativeQuery("delete x from stock x where x.assets_id = :assets_id and x.ticker = :ticker")
                    .setParameter("assets_id", assets_id)
                    .setParameter("ticker", ticker);
    query.executeUpdate();
  }
}
