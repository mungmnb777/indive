package com.ssafy.indive.domain.member.service.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceMemberModifyRequestDto {

    @NotBlank
    private String nickname;

    private String profileMessage;

    private MultipartFile profileFile;

    private MultipartFile backgroundFile;


    @Builder
    public ServiceMemberModifyRequestDto(String nickname, String profileMessage, MultipartFile profileFile, MultipartFile backgroundFile) {
        this.nickname = nickname;
        this.profileMessage = profileMessage;
        this.profileFile = profileFile;
        this.backgroundFile = backgroundFile;
    }
}
