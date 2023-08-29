package com.db.trade.engine.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.db.trade.engine.constants.Constants;
import com.db.trade.engine.dao.TradeRepository;
import com.db.trade.engine.dao.TradeUpdateRepository;
import com.db.trade.engine.model.Trade;
import com.db.trade.engine.util.HttpClientUtil;

@Service
public class TradeService {

	@Value("${trade.engine.validator}")
	private String validatorApiURI;
	
	private static final Logger log = LoggerFactory.getLogger(TradeService.class);
	
	@Autowired
	TradeRepository tradeRepository;
	
	@Autowired
	TradeUpdateRepository tradeUpdateRepository;

	public boolean isValid(Trade trade){
		return (boolean) HttpClientUtil.httpClientCall(trade, validatorApiURI+Constants.ISVALID).getBody();
	}

	//Store should not allow the trade which has less maturity date then today date
	private boolean validateMaturityDate(Trade trade){
		return (boolean) HttpClientUtil.httpClientCall(trade, validatorApiURI+Constants.MATURITYDATE).getBody();
	}

	public void  persist(Trade trade){
		trade.setCreatedDate(LocalDate.now());
		tradeRepository.save(trade);
	}

	public List<Trade> findAll(){
		return  tradeRepository.findAll();
	}

	public void updateExpiryFlagOfTrade(){
		tradeRepository.findAllExpiryFlagN("N").stream().forEach(t -> {
			if (!validateMaturityDate(t)) {
				log.info("Trade which needs to updated {}", t);
				tradeUpdateRepository.upsertExpiredFlag(t.get_id());
			}
		});
	}
	
	


}
