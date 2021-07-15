package com.PortfolioTracker;

import com.PortfolioTracker.DAO.UserDAO;
import com.PortfolioTracker.Util.IexApiUtil;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{
	@Autowired
	private UserDAO userDao;
	@Autowired
	private IexApiUtil iexApiUtil;

	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List tickers = List.of("aapl","fb","tsla");
		iexApiUtil.getStockQuoteDtoBatchRequestByTickers(tickers);
		/*
		iexApiUtil.getStockInfoDTO("twtr");
		System.out.println("NEW ====================================================================================");
		System.out.println(iexApiUtil.getStockInfoDTO("twtr"));
		
		/*
		if( userDao.userNameTaken("user")){
			System.out.println("Taken");
		}else{
			System.out.println("Not taken");
		}
		System.out.println(userDao.getAll("user"));
		
		
		
		System.out.println(userDao.getByUsername("user"));
		*/
	}

}
