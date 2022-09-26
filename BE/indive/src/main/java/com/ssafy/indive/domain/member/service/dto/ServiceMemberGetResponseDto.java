package com.ssafy.indive.domain.member.service.dto;

import com.ssafy.indive.domain.member.entity.enumeration.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceMemberGetResponseDto {


    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private Role role;

    private String wallet;


    private String profileMessage;


    private String notice;


    @Builder

    public ServiceMemberGetResponseDto(String email, String password, String nickname, Role role, String wallet, String profileMessage, String notice) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.wallet = wallet;
        this.profileMessage = profileMessage;
        this.notice = notice;
    }
}
