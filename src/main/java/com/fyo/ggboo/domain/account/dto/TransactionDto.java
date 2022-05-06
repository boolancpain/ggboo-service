package com.fyo.ggboo.domain.account.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

/**
 * Transaction Dto
 * 
 * @author boolancpain
 */
@Getter
public class TransactionDto {
	
	private Long transactionId;
	
	private Long memberId;
	
	private Long transactionTypeId;
	
	private Long categoryId;
	
	private int cost;
	
	private String remark;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime transactedDate;
	
	@Builder
	public TransactionDto(Long transactionId, Long memberId, Long transactionTypeId, Long categoryId, int cost, String remark, LocalDateTime transactedDate) {
		this.transactionId = transactionId;
		this.memberId = memberId;
		this.transactionTypeId = transactionTypeId;
		this.categoryId = categoryId;
		this.cost = cost;
		this.remark = remark;
		this.transactedDate = transactedDate;
	}
}