package com.fyo.ggboo.domain.account.dto;

import com.fyo.ggboo.infra.validator.DateConstraint;

import lombok.Getter;
import lombok.Setter;

/**
 * Transaction Search Dto
 * 
 * @author boolancpain
 */
@Getter
@Setter
public class AccountTransactionDto {
	
	@DateConstraint(message = "{validation.date}")
	private String date;
}