package com.db.trade.engine.controller;

import com.db.trade.engine.config.TradeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	private final TradeProperties properties;

	public HomeController(TradeProperties properties) {
		this.properties = properties;
	}

	@Value("${trade.message} : Trade Engine Home")
	private String message="Trade Engine 1";

/*	@GetMapping(Constants.BASE_TRADE)
	public  String findAllTrades(){
		return message;
	} */
      @GetMapping("/")
	public TradeProperties home(){
		return properties;
	}
}
