package com.PortfolioTracker;

import com.PortfolioTracker.DAO.AssetsDAO;
import com.PortfolioTracker.DAO.UserDAO;
import com.PortfolioTracker.Entities.Assets;
import com.PortfolioTracker.Entities.Stock;
import com.PortfolioTracker.Entities.User;
import com.PortfolioTracker.Pojos.StockPojo;

import com.PortfolioTracker.Services.UserService;
import com.PortfolioTracker.Util.IexApiUtil;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{
	@Autowired
	private UserDAO userDao;
	@Autowired
	private AssetsDAO assetsDAO;
	@Autowired
	private IexApiUtil iexApiUtil;
	@Autowired
	private UserService userService;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

}
