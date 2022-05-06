package com.fyo.ggboo.domain.account.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Account Dto
 * 
 * @author boolancpain
 */
@Getter
public class AccountDto {
	
	private Long accountId;
	
	@Builder
	public AccountDto(Long accountId) {
		this.accountId = accountId;
	}
}