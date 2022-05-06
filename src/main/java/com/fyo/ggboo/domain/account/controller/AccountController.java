package com.fyo.ggboo.domain.account.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fyo.ggboo.domain.account.dto.AccountTransactionDto;
import com.fyo.ggboo.domain.account.dto.TransactionCreateDto;
import com.fyo.ggboo.domain.account.service.AccountService;
import com.fyo.ggboo.domain.account.service.TransactionService;

/**
 * Account Controller
 * 
 * @author boolancpain
 */
@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	/**
	 * 장부 생성
	 */
	@PostMapping
	public ResponseEntity<?> createAccount() {
		return accountService.createAccount();
	}
	
	/**
	 * 보유 장부 조회
	 */
	@GetMapping
	public ResponseEntity<?> getMyAccount() {
		return accountService.getMyAccount();
	}
	
	/**
	 * 장부 삭제
	 * 
	 * @param accountId
	 */
	@DeleteMapping(value = "/{accountId}")
	public ResponseEntity<?> deleteAccount(@PathVariable Long accountId) {
		return accountService.deleteAccount(accountId);
	}
	
	/**
	 * 거래유형 조회
	 */
	@GetMapping(value = "/transaction/type")
	public ResponseEntity<?> getTransactionTypes() {
		return transactionService.getTransactionTypes();
	}
	
	/**
	 * 거래내역 목록 조회
	 * 
	 * @param accountId
	 * @param accountTransactionDto
	 */
	@GetMapping(value = "/{accountId}/transaction")
	public ResponseEntity<?> getTransactions(@PathVariable Long accountId, @Valid AccountTransactionDto accountTransactionDto) {
		return transactionService.getTransactions(accountId, accountTransactionDto);
	}
	
	/**
	 * 거래내역 생성
	 * 
	 * @param accountId
	 * @param transactionCreateDto
	 */
	@PostMapping(value = "/{accountId}/transaction")
	public ResponseEntity<?> createTransaction(@PathVariable Long accountId, @Valid @RequestBody TransactionCreateDto transactionCreateDto) {
		return transactionService.createTransaction(accountId, transactionCreateDto);
	}
	
	/**
	 * 거래내역 조회
	 * 
	 * @param accountId
	 * @param transactionId
	 */
	@GetMapping(value = "/{accountId}/transaction/{transactionId}")
	public ResponseEntity<?> getTransaction(@PathVariable Long accountId, @PathVariable Long transactionId) {
		return transactionService.getTransaction(accountId, transactionId);
	}
	
	/**
	 * 거래내역 수정
	 * 
	 * @param accountId
	 * @param transactionId
	 * @param transactionCreateDto
	 */
	@PutMapping(value = "/{accountId}/transaction/{transactionId}")
	public ResponseEntity<?> updateTransaction(@PathVariable Long accountId, @PathVariable Long transactionId, @Valid @RequestBody TransactionCreateDto transactionCreateDto) {
		return transactionService.updateTransaction(accountId, transactionId, transactionCreateDto);
	}
	
	/**
	 * 거래내역 삭제
	 * 
	 * @param accountId
	 * @param transactionId
	 */
	@DeleteMapping(value = "/{accountId}/transaction/{transactionId}")
	public ResponseEntity<?> deleteTransaction(@PathVariable Long accountId, @PathVariable Long transactionId) {
		return transactionService.deleteTransaction(accountId, transactionId);
	}
}