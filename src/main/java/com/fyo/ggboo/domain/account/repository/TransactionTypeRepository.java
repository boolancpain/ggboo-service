package com.fyo.ggboo.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fyo.ggboo.domain.account.entity.TransactionType;

/**
 * Transaction Type Repository
 * 
 * @author boolancpain
 */
@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
	
}