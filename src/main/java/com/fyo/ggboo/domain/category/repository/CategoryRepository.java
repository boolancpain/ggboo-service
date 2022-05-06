package com.fyo.ggboo.domain.category.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fyo.ggboo.domain.category.entity.Category;

/**
 * Category Repository
 * 
 * @author boolancpain
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	/**
	 * 장부 id와 카테고리명으로 카테고리 유무 조회
	 * 
	 * @param memberId
	 * @param description
	 */
	public boolean existsByAccountIdAndAlias(Long accountId, String alias);
	
	/**
	 * 장부 id와 카테고리 id로 카테고리 조회
	 * 
	 * @param id
	 * @param memberId
	 */
	public Optional<Category> findByIdAndAccountId(Long id, Long accountId);
	
	/**
	 * 장부 id로 카테고리 삭제
	 * 
	 * @param accountId
	 */
	public void deleteAllByAccountId(Long accountId);
}