package com.ssafy.indive.domain.member.service.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceDuplicatedEmail {

    @NotBlank
    private String email;


    @Builder
    public ServiceDuplicatedEmail(String email) {
        this.email = email;
    }

    public String convertToString(){
        return email;
    }
}
