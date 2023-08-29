package com.db.trade.engine.model;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;
import com.db.trade.engine.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data

@Document(collection = Constants.TRADE_COLLECTION)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class Trade {
	@JsonIgnore
	private String _id;
    private String tradeId;
    private int version;
    private String counterParty;
    private String bookId;
    private LocalDate maturityDate;
    private LocalDate createdDate;
    private String expiredFlag;
    @JsonIgnore
    private String exception;
}
