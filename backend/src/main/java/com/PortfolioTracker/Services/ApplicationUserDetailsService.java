package com.PortfolioTracker.Services;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.PortfolioTracker.DAO.UserDAO;
import com.PortfolioTracker.Entities.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

  @Autowired
  private UserDAO userDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userDao.getByUsername(username);
  }

  
}
