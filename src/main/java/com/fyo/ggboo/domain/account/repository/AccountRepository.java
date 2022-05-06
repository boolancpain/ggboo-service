package com.fyo.ggboo.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fyo.ggboo.domain.account.entity.Account;

/**
 * Account Repository
 * 
 * @author boolancpain
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
}