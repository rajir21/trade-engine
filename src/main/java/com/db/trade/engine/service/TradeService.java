package com.db.trade.engine.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
	public String isValid1(Trade trade){

		return (String) HttpClientUtil.httpClientCall(trade, validatorApiURI+Constants.ISVALID).getBody();

		/*
		ResponseEntity RE = (ResponseEntity)
				(HttpClientUtil.httpClientCall(trade, validatorApiURI+Constants.ISVALID).getBody());

		//return (String) (HttpClientUtil.httpClientCall(trade, validatorApiURI+Constants.ISVALID).getBody()).toString();
	   return RE.toString(); */
	}
	public boolean isValid(Trade trade){

		return (boolean) HttpClientUtil.httpClientCall(trade, validatorApiURI+Constants.ISVALID).getBody();
	}

	//Store should not allow the trade which has less maturity date then today date
	private boolean validateMaturityDate(Trade trade){
		return (boolean) HttpClientUtil.httpClientCall(trade, validatorApiURI+Constants.MATURITYDATE).getBody();
	}

	public void  persist(Trade trade){
		trade.setCreatedDate(LocalDate.now());
		//if(trade!=null && trade.getStatus()!=null && trade.getStatus().equals("INSERT"))
		tradeRepository.save(trade);
		/*else {
			log.info("Trade which needs to updated {}", trade);
			tradeUpdateRepository.upsertExpiredFlag(trade.get_id());
		}*/
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
