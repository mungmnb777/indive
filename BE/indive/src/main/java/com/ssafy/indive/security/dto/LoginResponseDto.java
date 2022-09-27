package com.ssafy.indive.security.dto;

import com.ssafy.indive.domain.member.entity.enumeration.Role;
import lombok.Data;

@Data
public class LoginResponseDto {

	private Long seq;


	private String email;


	private String password;


	private String nickname;


	private Role role;

	private String wallet;

	private String profileMessage;

	private String notice;

	public LoginResponseDto(){

	}

	public LoginResponseDto(Long seq, String email, String password, String nickname, Role role, String wallet, String profileMessage, String notice) {
		this.seq = seq;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.role = role;
		this.wallet = wallet;
		this.profileMessage = profileMessage;
		this.notice = notice;
	}
}
