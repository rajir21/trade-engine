package com.db.trade.engine.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.db.trade.engine.constants.Constants;
import com.db.trade.engine.exception.ApiRequestException;
import com.db.trade.engine.exception.InvalidTradeException;
import com.db.trade.engine.model.Trade;
import com.db.trade.engine.service.TradeService;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class TradeController extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(TradeController.class);
	@Autowired
	TradeService tradeService;

	@PostMapping(Constants.TRADE)
	public ResponseEntity<Object> tradeValidateStore(@RequestBody Trade trade){
		//try {
			if(tradeService.isValid(trade)) {
				tradeService.persist(trade);
			}else{
				throw new InvalidTradeException(trade.getTradeId()+"  Trade Id is not found");
			}
		/*}catch(Exception e) {
			log.error("Exception Occured in isValid {}",e.getMessage());
			throw new ApiRequestException(Constants.ERROR);
		}*/
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	@ExceptionHandler(InvalidTradeException.class)
	public ResponseEntity<VndErrors> notFoundException(final InvalidTradeException e) {
		return error(e, HttpStatus.NOT_ACCEPTABLE, e.getId());
	}

	@GetMapping(Constants.TRADE)
	public List<Trade> findAllTrades(){
		return tradeService.findAll();
	}

	private ResponseEntity<VndErrors> error(
			final Exception exception, final HttpStatus httpStatus, final String logRef) {
		final String message =
				Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
		return new ResponseEntity<>(new VndErrors(logRef, message), httpStatus);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<VndErrors> assertionException(final IllegalArgumentException e) {
		return error(e, HttpStatus.NOT_ACCEPTABLE, e.getLocalizedMessage());
	}
}
