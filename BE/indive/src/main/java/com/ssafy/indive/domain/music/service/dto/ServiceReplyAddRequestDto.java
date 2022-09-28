package com.ssafy.indive.domain.music.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceReplyAddRequestDto {

    private String content;

    public ServiceReplyAddRequestDto(String content) {
        this.content = content;
    }
}
