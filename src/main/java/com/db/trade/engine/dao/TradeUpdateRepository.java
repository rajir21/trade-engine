package com.db.trade.engine.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.db.trade.engine.model.Trade;

@Repository
public class TradeUpdateRepository {
	@Autowired
	private MongoTemplate mongoTemplate;

	public void upsertExpiredFlag(String id){
		Query query = new Query().addCriteria(Criteria.where("_id").is(id));
		Update updateExpiredFlag = new Update().set("expiredFlag", "Y");
		mongoTemplate.upsert(query, updateExpiredFlag, Trade.class);
	}
	public void upsertTrade(Trade trade){
		Query query = new Query().addCriteria(Criteria.where("_id").is(trade.get_id() ));
		Update updateExpiredFlag = new Update().set("expiredFlag", trade.getTradeId());
	       updateExpiredFlag = new Update().set("expiredFlag", trade.getTradeId());
		mongoTemplate.upsert(query, updateExpiredFlag, Trade.class);
	}
}
