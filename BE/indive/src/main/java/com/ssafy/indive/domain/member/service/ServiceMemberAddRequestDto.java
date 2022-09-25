package com.ssafy.indive.domain.member.service;

import lombok.*;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceMemberAddRequestDto {


    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private String wallet;

    @NotBlank
    private String profileMessage;


    @Builder
    public ServiceMemberAddRequestDto(String email, String password, String nickname, String wallet, String profileMessage) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.wallet = wallet;
        this.profileMessage = profileMessage;
    }
}
