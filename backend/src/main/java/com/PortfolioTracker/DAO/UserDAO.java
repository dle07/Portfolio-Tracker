package com.PortfolioTracker.DAO;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.PortfolioTracker.Entities.Stock;
import com.PortfolioTracker.Entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserDAO {

    @Autowired
    private EntityManager em;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    
    public User getByUsername(String username) {
        TypedQuery<User> query = em.createQuery("select u from User u where u.username= :user_name",User.class);
                    query.setParameter("user_name", username);
        User queried_user = query.getSingleResult();

        return queried_user;
    }

    public Boolean userNameTaken( String username ){

        Query query = em.createQuery("select count(u) from User u where u.username= :user_name");
                    query.setParameter("user_name", username);
        Long count = (Long) query.getSingleResult();
        return( count.equals(0L)? false: true);

    }

    public void insertUser(String username, String password){
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            User newUser = new User(username, encodedPassword);
            em.persist(newUser);
    }
    
    public Integer getIdByUsername( String username ){
        
        TypedQuery<Integer> query = em.createQuery("select x.id from User x where x.username = :user_name", Integer.class)
                                        .setParameter("user_name", username);
        return query.getSingleResult();
    }
    
    
}
