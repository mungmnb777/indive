package com.ssafy.indive.domain.member.controller.dto;

import com.ssafy.indive.domain.member.service.ServiceMemberAddRequestDto;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicAddRequestDto;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebMemberAddRequestDto {

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


    public ServiceMemberAddRequestDto convertToServiceDto() {
        return ServiceMemberAddRequestDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .wallet(wallet)
                .profileMessage(profileMessage)
                .build();
    }


    @Builder
    public WebMemberAddRequestDto(String email, String password, String nickname, String wallet, String profileMessage) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.wallet = wallet;
        this.profileMessage = profileMessage;
    }
}
