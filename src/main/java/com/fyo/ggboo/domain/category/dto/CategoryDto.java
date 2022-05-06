package com.fyo.ggboo.domain.category.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Category Dto
 * 
 * @author boolancpain
 */
@Getter
public class CategoryDto {
	
	private Long categoryId;
	
	private String alias;
	
	private int sequence;
	
	@Builder
	public CategoryDto(Long categoryId, String alias, int sequence) {
		this.categoryId = categoryId;
		this.alias = alias;
		this.sequence = sequence;
	}
}