package com.fyo.ggboo.domain.category.dto;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * Category Create Dto
 * 
 * @author boolancpain
 */
@Getter
@Setter
public class CategoryCreateDto {
	
	@Size(min = 2, max = 10, message = "{validation.category.alias}")
	private String alias;
}