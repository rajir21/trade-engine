package com.db.trade.engine;

import com.db.trade.engine.config.TradeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(TradeProperties.class)
@EnableScheduling
@EnableMongoRepositories
public class TradeEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeEngineApplication.class, args);
	}

}
