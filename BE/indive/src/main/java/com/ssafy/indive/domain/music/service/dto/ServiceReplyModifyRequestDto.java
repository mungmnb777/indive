package com.ssafy.indive.domain.music.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceReplyModifyRequestDto {

    private String content;

    public ServiceReplyModifyRequestDto(String content) {
        this.content = content;
    }
}
