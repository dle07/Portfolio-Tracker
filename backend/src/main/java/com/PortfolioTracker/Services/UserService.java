package com.PortfolioTracker.Services;

import com.PortfolioTracker.DAO.UserDAO;
import com.PortfolioTracker.DTOs.UserRegistrationDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  
  @Autowired
  UserDAO userDAO;

  public void registerUser(UserRegistrationDTO newUser) throws IllegalArgumentException{
    userDAO.insertUser(newUser.getUsername(), newUser.getPassword());
  }
}
