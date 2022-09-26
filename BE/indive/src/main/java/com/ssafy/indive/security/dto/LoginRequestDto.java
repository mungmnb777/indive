package com.ssafy.indive.security.dto;

import com.ssafy.indive.domain.member.entity.enumeration.Role;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
public class LoginRequestDto {

	private Long seq;


	private String email;


	private String password;


	private String nickname;


	private Role role;

	private String wallet;

	private String imageOrigin;

	private String imageUuid;

	private String backgroundOrigin;

	private String backgroundUuid;

	private String profileMessage;

	private String notice;

	public LoginRequestDto(){

	}
	public LoginRequestDto(Long seq, String email, String password, String nickname, Role role, String wallet, String imageOrigin, String imageUuid, String backgroundOrigin, String backgroundUuid, String profileMessage, String notice) {
		this.seq = seq;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.role = role;
		this.wallet = wallet;
		this.imageOrigin = imageOrigin;
		this.imageUuid = imageUuid;
		this.backgroundOrigin = backgroundOrigin;
		this.backgroundUuid = backgroundUuid;
		this.profileMessage = profileMessage;
		this.notice = notice;
	}
}
