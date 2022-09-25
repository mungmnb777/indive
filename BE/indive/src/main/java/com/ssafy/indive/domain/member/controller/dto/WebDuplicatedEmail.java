package com.ssafy.indive.domain.member.controller.dto;

import com.ssafy.indive.domain.member.service.dto.ServiceDuplicatedEmail;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberAddRequestDto;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebDuplicatedEmail {

    @NotBlank
    private String email;


    public ServiceDuplicatedEmail convertToServiceDto() {
        return ServiceDuplicatedEmail.builder()
                .email(email)
                .build();

    }


    @Builder
    public WebDuplicatedEmail(String email) {
        this.email = email;
    }
}
