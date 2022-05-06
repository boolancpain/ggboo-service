package com.fyo.ggboo.domain.category.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fyo.ggboo.domain.category.dto.CategoryCreateDto;
import com.fyo.ggboo.domain.category.dto.CategoryReorderDto;
import com.fyo.ggboo.domain.category.service.CategoryService;

/**
 * Category Controller
 * 
 * @author boolancpain
 */
@RestController
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 카테고리 목록 조회
	 * 
	 * @param accountId
	 */
	@GetMapping(value = "/account/{accountId}/category")
	public ResponseEntity<?> getCategories(@PathVariable Long accountId) {
		return categoryService.getCategories(accountId);
	}
	
	/**
	 * 카테고리 생성
	 * 
	 * @param accountId
	 * @param categoryCreateDto
	 */
	@PostMapping(value = "/account/{accountId}/category")
	public ResponseEntity<?> createCategory(@PathVariable Long accountId, @Valid @RequestBody CategoryCreateDto categoryCreateDto) {
		return categoryService.createCategory(accountId, categoryCreateDto);
	}
	
	/**
	 * 카테고리 조회
	 * 
	 * @param accountId
	 * @param categoryId
	 */
	@GetMapping(value = "/account/{accountId}/category/{categoryId}")
	public ResponseEntity<?> getCategory(@PathVariable Long accountId, @PathVariable Long categoryId) {
		return categoryService.getCategory(accountId, categoryId);
	}
	
	/**
	 * 카테고리 수정
	 * 
	 * @param accountId
	 * @param categoryId
	 * @param categoryCreateDto
	 */
	@PutMapping(value = "/account/{accountId}/category/{categoryId}")
	public ResponseEntity<?> getCategory(@PathVariable Long accountId, @PathVariable Long categoryId, @Valid @RequestBody CategoryCreateDto categoryCreateDto) {
		return categoryService.updateCategory(accountId, categoryId, categoryCreateDto);
	}
	
	/**
	 * 카테고리 삭제
	 * 
	 * @param accountId
	 * @param categoryId
	 */
	@DeleteMapping(value = "/account/{accountId}/category/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable Long accountId, @PathVariable Long categoryId) {
		return categoryService.deleteCategory(accountId, categoryId);
	}
	
	/**
	 * 카테고리 정렬순서 수정
	 * 
	 * @param accountId
	 * @param categoryReorderDto
	 */
	@PutMapping(value = "/account/{accountId}/category/reorder")
	public ResponseEntity<?> updateCategorySequence(@PathVariable Long accountId, @Valid @RequestBody CategoryReorderDto categoryReorderDto) {
		return categoryService.updateCategorySequence(accountId, categoryReorderDto);
	}
}