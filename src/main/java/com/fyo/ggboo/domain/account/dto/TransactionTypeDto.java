package com.fyo.ggboo.domain.account.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Transaction Type Dto
 * 
 * @author boolancpain
 */
@Getter
public class TransactionTypeDto {
	
	private Long transactionTypeId;
	
	private String alias;
	
	@Builder
	public TransactionTypeDto(Long transactionTypeId, String alias) {
		this.transactionTypeId = transactionTypeId;
		this.alias = alias;
	}
}