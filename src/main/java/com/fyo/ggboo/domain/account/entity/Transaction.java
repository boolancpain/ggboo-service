package com.fyo.ggboo.domain.account.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fyo.ggboo.domain.category.entity.Category;
import com.fyo.ggboo.domain.member.entity.Member;
import com.fyo.ggboo.global.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Transaction Class 
 * 
 * @author boolancpain
 */
@Entity
@Table(name = "transaction")
@Getter
@NoArgsConstructor
@DynamicUpdate
public class Transaction extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "type_id")
	private TransactionType transactionType;
	
	@Column(nullable = false)
	private int cost;
	
	@Column(length = 255)
	private String remark;
	
	@Column(columnDefinition="DATETIME(0) default CURRENT_TIMESTAMP")
	private LocalDateTime transactedDate;
	
	@Builder
	public Transaction(Member member, Account account, Category category, TransactionType transactionType, int cost, String remark, LocalDateTime transactedDate) {
		this.member = member;
		this.account = account;
		this.category = category;
		this.transactionType = transactionType;
		this.cost = cost;
		this.remark = remark;
		this.transactedDate = transactedDate;
	}
	
	/**
	 * 거래유형 업데이트
	 * 
	 * @param transactionType
	 */
	public void updateTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
	/**
	 * 카테고리 업데이트
	 * 
	 * @param category
	 */
	public void updateCategory(Category category) {
		this.category = category;
	}
	
	/**
	 * 금액 업데이트
	 * 
	 * @param cost
	 */
	public void updateCost(int cost) {
		this.cost = cost;
	}
	
	/**
	 * 비고 업데이트
	 * 
	 * @param remark
	 */
	public void updateRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 거래일 업데이트
	 * 
	 * @param transactedDate
	 */
	public void updateTransactedDate(LocalDateTime transactedDate) {
		this.transactedDate = transactedDate;
	}
}