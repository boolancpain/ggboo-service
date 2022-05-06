package com.fyo.ggboo.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 토큰 Dto
 * 
 * @author boolancpain
 */
@Getter
@NoArgsConstructor
public class TokenDto {
	
	private String token;
	
	@Builder
	public TokenDto(String token) {
		this.token = token;
	}
}