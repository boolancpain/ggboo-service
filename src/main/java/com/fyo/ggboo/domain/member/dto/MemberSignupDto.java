package com.fyo.ggboo.domain.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;

/**
 * 회원 가입 Dto
 * 
 * @author boolancpain
 */
@Getter
public class MemberSignupDto {
	
	@NotBlank(message = "{validation.required}")
	@Pattern(regexp = "[a-zA-Z0-9]{4,12}", message = "{validation.member.id}")
	private String memberId;
	
	@NotBlank(message = "{validation.required}")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$", message = "{validation.member.password}")
	private String password;
	
	@NotBlank(message = "{validation.required}")
	@Size(min = 2, max = 10, message = "{validation.member.name}")
	private String memberName;
	
	@Builder
	public MemberSignupDto(String memberId, String password, String memberName) {
		this.memberId = memberId;
		this.password = password;
		this.memberName = memberName;
	}
}