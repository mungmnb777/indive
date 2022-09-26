package com.ssafy.indive.domain.member.controller.dto;

import com.ssafy.indive.domain.member.service.dto.ServiceMemberModifyRequestDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebMemberModifyRequestDto {
    @NotBlank
    private String nickname;

    private String profileMessage;

    private MultipartFile profileFile;

    private MultipartFile backgroundFile;


    @Builder
    public ServiceMemberModifyRequestDto convertToServiceDto() {
        return ServiceMemberModifyRequestDto.builder()
            .nickname(nickname)
            .profileMessage(profileMessage)
            .profileFile(profileFile)
            .backgroundFile(backgroundFile)
            .build();
    }

    @Builder
    public WebMemberModifyRequestDto(String nickname, String profileMessage, MultipartFile profileFile, MultipartFile backgroundFile) {
        this.nickname = nickname;
        this.profileMessage = profileMessage;
        this.profileFile = profileFile;
        this.backgroundFile = backgroundFile;
    }

    public void update(ServiceMemberModifyRequestDto dto){
        this.nickname = dto.getNickname();
        this.profileMessage = dto.getProfileMessage();
    }
}
