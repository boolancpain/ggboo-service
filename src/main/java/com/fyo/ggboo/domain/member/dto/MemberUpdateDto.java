package com.fyo.ggboo.domain.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원 수정 Dto
 * 
 * @author boolancpain
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateDto {
	
	@NotBlank(message = "{validation.required}")
	@Size(min = 2, max = 10, message = "{validation.member.name}")
	private String memberName;
	
	@Builder
	public MemberUpdateDto(String memberName) {
		this.memberName = memberName;
	}
}