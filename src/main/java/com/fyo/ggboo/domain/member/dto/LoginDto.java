package com.fyo.ggboo.domain.member.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 Dto
 * 
 * @author boolancpain
 */
@Getter
@Setter
public class LoginDto {
	
	@NotBlank(message = "{validation.required}")
	private String id;
	
	@NotBlank(message = "{validation.required}")
	private String password;
	
	@Builder
	public LoginDto(String id, String password) {
		this.id = id;
		this.password = password;
	}
}