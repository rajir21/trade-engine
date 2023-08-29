package com.db.trade.engine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "td")
public record TradeProperties(String tradeName, String tradeValue) {


}
