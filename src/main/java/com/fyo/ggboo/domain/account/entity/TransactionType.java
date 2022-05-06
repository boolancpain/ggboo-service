package com.fyo.ggboo.domain.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Transaction Type Entity
 * 
 * @author boolancpain
 */
@Entity
@Table(name = "transaction_type")
@Getter
@NoArgsConstructor
public class TransactionType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 10)
	private String alias;
}