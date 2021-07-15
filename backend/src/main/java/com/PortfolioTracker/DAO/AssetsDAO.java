package com.PortfolioTracker.DAO;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.PortfolioTracker.Entities.Stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class AssetsDAO {

  @Autowired
  private UserDAO userDAO;
  @Autowired
  private EntityManager em;
  

  @SuppressWarnings("unchecked")
  public ArrayList<Stock> getAllStocksByUsername( final String user_name ){
    int user_id = userDAO.getIdByUsername(user_name);

    Query query = em.createQuery("select x.stocks_owned from Assets x where x.user_id_fk = : user_id")
    .setParameter("user_id", user_id);

    return new ArrayList<Stock> ( query.getResultList());
  }

  public double getTotalAssetMarketValue(int user_id){
    
  }
}
