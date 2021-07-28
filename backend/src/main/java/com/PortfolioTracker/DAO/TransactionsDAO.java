package com.PortfolioTracker.DAO;
import com.PortfolioTracker.DTOs.TransactionDTO;
import com.PortfolioTracker.Entities.Transaction;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class TransactionsDAO {
  
  @Autowired
  private EntityManager em;
  @Autowired
  private UserDAO userDAO;

  public void insert(Transaction transaction){
    em.persist(transaction);
  }

  
  @SuppressWarnings("unchecked")
  public ArrayList<TransactionDTO> getAllPastTransactions( final String user_name){
    ArrayList<Transaction> temp;
    int assets_id = userDAO.get_assets_id(user_name);
    Query query = em.createNativeQuery("select * from transactions x where x.assets_id = :assets_id", Transaction.class)
                        .setParameter("assets_id", assets_id);
    temp = new ArrayList<Transaction>(query.getResultList());

    ArrayList<TransactionDTO> result = new ArrayList<TransactionDTO>();
    for( Transaction transaction: temp){
      result.add( new TransactionDTO(transaction));
    }
    return result;
  }
  
}
