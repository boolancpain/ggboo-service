package com.fyo.ggboo.domain.category.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * Category Reorder Dto
 * 
 * @author boolancpain
 */
@Getter
@Setter
public class CategoryReorderDto {
	
	@NotEmpty(message= "{validation.required}")
	private List<Long> categoryList;
}