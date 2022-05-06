package com.fyo.ggboo.domain.account.service;

import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fyo.ggboo.domain.account.dto.AccountDto;
import com.fyo.ggboo.domain.account.entity.Account;
import com.fyo.ggboo.domain.account.repository.AccountRepository;
import com.fyo.ggboo.domain.account.repository.TransactionRepository;
import com.fyo.ggboo.domain.category.entity.Category;
import com.fyo.ggboo.domain.category.repository.CategoryRepository;
import com.fyo.ggboo.domain.member.entity.Member;
import com.fyo.ggboo.domain.member.repository.MemberRepository;
import com.fyo.ggboo.global.SecurityUtil;
import com.fyo.ggboo.global.enums.DefaultCategory;
import com.fyo.ggboo.global.response.BaseResponse;
import com.fyo.ggboo.infra.message.CustomMessageSource;

/**
 * Account Service
 * 
 * @author boolancpain
 */
@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CustomMessageSource customMessageSource;
	
	/**
	 * 장부 생성
	 */
	@Transactional
	public ResponseEntity<?> createAccount() {
		// 로그인 회원
		Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new UsernameNotFoundException(null));
		
		// 장부 유무 체크
		if(member.getAccount() != null) {
			// 409
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(BaseResponse.builder()
							.message(customMessageSource.getMessage("account.exists"))
							.build());
		}
		
		// 새 장부 생성
		Account account = Account.builder().build();
		
		// 회원 장부 추가
		member.updateAccount(account);
		
		// account save
		accountRepository.save(account);
		
		// 기본 카테고리 생성
		Stream.of(DefaultCategory.values()).forEach(defaultCategory -> {
			// 카테고리 생성
			Category category = Category.builder()
					.accountId(account.getId())
					.alias(defaultCategory.getAlias())
					.sequence(defaultCategory.getSequence())
					.build();
			
			// save
			categoryRepository.save(category);
		});
		
		AccountDto accountDto = AccountDto.builder()
				.accountId(account.getId())
				.build();
		
		// 200
		return ResponseEntity.ok(accountDto);
	}
	
	/**
	 * 회원 장부 조회
	 */
	public ResponseEntity<?> getMyAccount() {
		// 로그인 회원
		Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new UsernameNotFoundException(null));
		
		Account account = member.getAccount();
		
		// 200
		return ResponseEntity.ok(account == null ? null : AccountDto.builder()
				.accountId(account.getId())
				.build());
	}
	
	/**
	 * 장부 삭제
	 * 
	 * @param accountId
	 */
	@Transactional
	public ResponseEntity<?> deleteAccount(Long accountId) {
		// 로그인 회원
		Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
				.orElseThrow(() -> new UsernameNotFoundException(null));
		
		// 장부 조회
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new EntityNotFoundException(customMessageSource.getMessage("account.notfound", accountId)));
		
		// 장부의 회원 목록에서 제거
		account.removeMember(member.getId());
		
		// 회원의 장부 키 삭제
		member.updateAccount(null);
		
		// 회원 정보 업데이트
		memberRepository.save(member);
		
		// 장부를 소유한 회원이 없는 경우 장부와 관련 정보 모두 삭제
		if(account.getMembers().isEmpty()) {
			// 카테고리 삭제
			categoryRepository.deleteAllByAccountId(accountId);
			
			// 거래내역 삭제
			transactionRepository.deleteAllByAccountId(accountId);
			
			// 장부 삭제
			accountRepository.deleteById(accountId);
		}
		
		// 204
		return ResponseEntity.noContent().build();
	}
}