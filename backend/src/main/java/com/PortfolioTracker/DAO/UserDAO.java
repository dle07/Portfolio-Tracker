package com.PortfolioTracker.DAO;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.PortfolioTracker.Entities.Stock;
import com.PortfolioTracker.Entities.User;

import org.hibernate.query.NativeQuery;
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
    
    public User getById(int user_id){
        TypedQuery<User> query = em.createQuery("select u from User u where u.id= :user_id",User.class);
                        query.setParameter("user_", user_id);
            User queried_user = query.getSingleResult();
    
            return queried_user;
    }
    
    public User getByUsername(String user_name) {
        TypedQuery<User> query = em.createQuery("select u from User u where u.username= :user_name",User.class);
                    query.setParameter("user_name", user_name);
        User queried_user = query.getSingleResult();

        return queried_user;
    }

    public Boolean userNameTaken( String user_name ){

        Query query = em.createQuery("select count(u) from User u where u.username= :user_name");
                    query.setParameter("user_name", user_name);
        Long count = (Long) query.getSingleResult();
        return( count.equals(0L)? false: true);

    }

    public void addUser(final String user_name, final String password){
            
            User newUser = new User(user_name, password);
            em.persist(newUser);
    }
    
    public Integer getIdByUsername( String user_name ){
        
        TypedQuery<Integer> query = em.createQuery("select x.id from User x where x.username = :user_name", Integer.class)
                                        .setParameter("user_name", user_name);
        return query.getSingleResult();
    }
    
    public int get_assets_id( String user_name){
        Query query = em.createNativeQuery("select x.assets_id_fk from users x where x.username = :user_name")
                        .setParameter("user_name", user_name);
        int result = (Integer)query.getSingleResult();
        return result;
    }
    }
    