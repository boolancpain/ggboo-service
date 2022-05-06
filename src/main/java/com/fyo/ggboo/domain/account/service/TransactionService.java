package com.fyo.ggboo.domain.account.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fyo.ggboo.domain.account.dto.AccountTransactionDto;
import com.fyo.ggboo.domain.account.dto.TransactionCreateDto;
import com.fyo.ggboo.domain.account.dto.TransactionDto;
import com.fyo.ggboo.domain.account.dto.TransactionTypeDto;
import com.fyo.ggboo.domain.account.entity.Account;
import com.fyo.ggboo.domain.account.entity.Transaction;
import com.fyo.ggboo.domain.account.entity.TransactionType;
import com.fyo.ggboo.domain.account.repository.TransactionRepository;
import com.fyo.ggboo.domain.account.repository.TransactionTypeRepository;
import com.fyo.ggboo.domain.category.entity.Category;
import com.fyo.ggboo.domain.category.repository.CategoryRepository;
import com.fyo.ggboo.domain.member.entity.Member;
import com.fyo.ggboo.domain.member.repository.MemberRepository;
import com.fyo.ggboo.global.DateUtil;
import com.fyo.ggboo.global.SecurityUtil;
import com.fyo.ggboo.global.response.BaseResponse;
import com.fyo.ggboo.infra.message.CustomMessageSource;

/**
 * Transaction Service
 * 
 * @author boolancpain
 */
@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private TransactionTypeRepository transactionTypeRepository;
	
	@Autowired
	private CustomMessageSource customMessageSource;
	
	@Autowired
	private DateUtil dateUtil;
	
	/**
	 * 거래유형 조회
	 */
	public ResponseEntity<?> getTransactionTypes() {
		List<TransactionTypeDto> transactionTypes = transactionTypeRepository.findAll().stream()
				.map(transactionType -> TransactionTypeDto.builder()
						.transactionTypeId(transactionType.getId())
						.alias(transactionType.getAlias())
						.build())
				.collect(Collectors.toList());
		
		// 200
		return ResponseEntity.ok(transactionTypes);
	}
	
	/**
	 * 거래내역 목록 조회
	 * 
	 * @param accountId
	 * @param accountTransactionDto
	 */
	public ResponseEntity<?> getTransactions(Long accountId, AccountTransactionDto accountTransactionDto) {
		// default now
		if(!StringUtils.hasText(accountTransactionDto.getDate())) {
			accountTransactionDto.setDate(LocalDate.now().toString());
		}
		
		// 검색기간 세팅
		LocalDateTime startDate = dateUtil.toStartLocalDateTime(accountTransactionDto.getDate());
		LocalDateTime endDate = dateUtil.toEndLocalDateTime(accountTransactionDto.getDate());
		
		// 거래내역
		List<TransactionDto> transactions = transactionRepository.findAllByAccountIdAndTransactedDateBetween(accountId, startDate, endDate).stream()
				.map(transaction -> TransactionDto.builder()
						.transactionId(transaction.getId())
						.memberId(transaction.getMember().getId())
						.transactionTypeId(transaction.getTransactionType().getId())
						.categoryId(transaction.getCategory().getId())
						.cost(transaction.getCost())
						.remark(transaction.getRemark())
						.transactedDate(transaction.getTransactedDate())
						.build())
				.collect(Collectors.toList());
		
		// 200
		return ResponseEntity.ok(transactions);
	}
	
	/**
	 * 거래내역 생성
	 * 
	 * @param accountId
	 * @param transactionCreateDto
	 */
	public ResponseEntity<?> createTransaction(Long accountId, TransactionCreateDto transactionCreateDto) {
		// 로그인 회원
		Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new UsernameNotFoundException(null));
		
		// 회원 장부 체크
		Account account = member.getAccount();
		if(account == null || account.getId() != accountId) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(BaseResponse.builder()
							.message(customMessageSource.getMessage("forbidden"))
							.build());
		}
		
		// 카테고리
		Category category = categoryRepository.findByIdAndAccountId(transactionCreateDto.getCategoryId(), accountId)
				.orElseThrow(() -> new EntityNotFoundException(customMessageSource.getMessage("category.notfound", transactionCreateDto.getCategoryId())));
		
		// 거래 유형
		TransactionType transactionType = transactionTypeRepository.findById(transactionCreateDto.getTransactionTypeId())
				.orElseThrow(() -> new EntityNotFoundException(customMessageSource.getMessage("transaction.type.notfound", transactionCreateDto.getTransactionTypeId())));
		
		// default now
		if(!StringUtils.hasText(transactionCreateDto.getTransactedDate())) {
			transactionCreateDto.setTransactedDate(LocalDate.now().toString()); 
		}
		
		// 거래일 입력
		LocalDateTime transactedDate = dateUtil.toLocalDateTime(transactionCreateDto.getTransactedDate());
		
		// 거래내역 생성
		Transaction transaction = Transaction.builder()
				.member(member)
				.account(account)
				.category(category)
				.transactionType(transactionType)
				.cost(transactionCreateDto.getCost())
				.remark(transactionCreateDto.getRemark())
				.transactedDate(transactedDate)
				.build();
		
		// save
		transactionRepository.save(transaction);
		
		TransactionDto transactionDto = TransactionDto.builder()
				.transactionId(transaction.getId())
				.memberId(member.getId())
				.transactionTypeId(transaction.getTransactionType().getId())
				.categoryId(transaction.getCategory().getId())
				.cost(transaction.getCost())
				.remark(transaction.getRemark())
				.transactedDate(transaction.getTransactedDate())
				.build();
		
		// 200
		return ResponseEntity.ok(transactionDto);
	}
	
	/**
	 * 거래내역 조회
	 * 
	 * @param accountId
	 * @param transactionId
	 */
	public ResponseEntity<?> getTransaction(Long accountId, Long transactionId) {
		// 로그인 회원
		Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new UsernameNotFoundException(null));
		
		// 회원 장부 체크
		Account account = member.getAccount();
		if(account == null || account.getId() != accountId) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(BaseResponse.builder()
							.message(customMessageSource.getMessage("forbidden"))
							.build());
		}
		
		// 거래내역
		Transaction transaction = transactionRepository.findByIdAndAccountId(transactionId, accountId)
				.orElseThrow(() -> new EntityNotFoundException(customMessageSource.getMessage("transaction.notfound", transactionId)));
		
		// convert to dto
		TransactionDto transactionDto = TransactionDto.builder()
				.transactionId(transaction.getId())
				.memberId(transaction.getMember().getId())
				.transactionTypeId(transaction.getTransactionType().getId())
				.categoryId(transaction.getCategory().getId())
				.cost(transaction.getCost())
				.remark(transaction.getRemark())
				.transactedDate(transaction.getTransactedDate())
				.build();
		
		// 200
		return ResponseEntity.ok(transactionDto);
	}
	
	/**
	 * 거래내역 수정
	 * 
	 * @param accountId
	 * @param transactionId
	 * @param transactionCreateDto
	 */
	public ResponseEntity<?> updateTransaction(Long accountId, Long transactionId, TransactionCreateDto transactionCreateDto) {
		// 로그인 회원
		Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new UsernameNotFoundException(null));
		
		// 회원 장부 체크
		Account account = member.getAccount();
		if(account == null || account.getId() != accountId) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(BaseResponse.builder()
							.message(customMessageSource.getMessage("forbidden"))
							.build());
		}
		
		// 거래내역
		Transaction transaction = transactionRepository.findByIdAndAccountId(transactionId, accountId)
				.orElseThrow(() -> new EntityNotFoundException(customMessageSource.getMessage("transaction.notfound", transactionId)));
		
		/* 변경 사항 업데이트 */
		// 1. 거래유형
		TransactionType transactionType = transactionTypeRepository.findById(transactionCreateDto.getTransactionTypeId())
				.orElseThrow(() -> new EntityNotFoundException(customMessageSource.getMessage("transaction.type.notfound", transactionCreateDto.getTransactionTypeId())));
		
		// update
		transaction.updateTransactionType(transactionType);
		
		// 2. 카테고리
		Category category = categoryRepository.findByIdAndAccountId(transactionCreateDto.getCategoryId(), accountId)
				.orElseThrow(() -> new EntityNotFoundException(customMessageSource.getMessage("category.notfound", transactionCreateDto.getCategoryId())));
		
		// update
		transaction.updateCategory(category);
		
		// 3. 금액
		// update
		transaction.updateCost(transactionCreateDto.getCost());
		
		// 4. 비고
		// update
		transaction.updateRemark(transactionCreateDto.getRemark());
		
		// 5. 거래일
		LocalDateTime transactedDate = dateUtil.toLocalDateTime(transactionCreateDto.getTransactedDate());
		
		// update
		transaction.updateTransactedDate(transactedDate);
		
		// save
		transactionRepository.save(transaction);
		
		// convert to dto
		TransactionDto transactionDto = TransactionDto.builder()
				.transactionId(transaction.getId())
				.memberId(transaction.getMember().getId())
				.transactionTypeId(transaction.getTransactionType().getId())
				.categoryId(transaction.getCategory().getId())
				.cost(transaction.getCost())
				.remark(transaction.getRemark())
				.transactedDate(transaction.getTransactedDate())
				.build();
		
		// 200
		return ResponseEntity.ok(transactionDto);
	}
	
	/**
	 * 거래내역 삭제
	 * 
	 * @param accountId
	 * @param transactionId
	 */
	public ResponseEntity<?> deleteTransaction(Long accountId, Long transactionId) {
		// 로그인 회원
		Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new UsernameNotFoundException(null));
		
		// 회원 장부 체크
		Account account = member.getAccount();
		if(account == null || account.getId() != accountId) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(BaseResponse.builder()
							.message(customMessageSource.getMessage("forbidden"))
							.build());
		}
		
		// 거래내역
		Transaction transaction = transactionRepository.findByIdAndAccountId(transactionId, accountId)
				.orElseThrow(() -> new EntityNotFoundException(customMessageSource.getMessage("transaction.notfound", transactionId)));
		
		// delte
		transactionRepository.delete(transaction);
		
		// 204
		return ResponseEntity.noContent().build();
	}
}