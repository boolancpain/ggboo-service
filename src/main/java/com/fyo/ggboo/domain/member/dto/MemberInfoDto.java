package com.fyo.ggboo.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 회원 정보 Dto
 * 
 * @author boolancpain
 */
@Getter
public class MemberInfoDto {
	
	private Long id;
	
	private String memberId;
	
	private String memberName;
	
	@Builder
	public MemberInfoDto(Long id, String memberId, String memberName) {
		this.id = id;
		this.memberId = memberId;
		this.memberName = memberName;
	}
}