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

    private MultipartFile image;

    private MultipartFile background;


    @Builder
    public ServiceMemberModifyRequestDto convertToServiceDto() {
        return ServiceMemberModifyRequestDto.builder()
            .nickname(nickname)
            .profileMessage(profileMessage)
            .image(image)
            .background(background)
            .build();
    }

    @Builder
    public WebMemberModifyRequestDto(String nickname, String profileMessage, MultipartFile image, MultipartFile backgroundFile) {
        this.nickname = nickname;
        this.profileMessage = profileMessage;
        this.image = image;
        this.background = backgroundFile;
    }

    public void update(ServiceMemberModifyRequestDto dto){
        this.nickname = dto.getNickname();
        this.profileMessage = dto.getProfileMessage();
    }
}
