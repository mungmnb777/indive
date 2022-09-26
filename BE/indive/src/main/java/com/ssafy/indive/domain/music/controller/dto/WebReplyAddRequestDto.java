package com.ssafy.indive.domain.music.controller.dto;

import com.ssafy.indive.domain.music.service.dto.ServiceReplyAddRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebReplyAddRequestDto {

    private String content;

    public WebReplyAddRequestDto(String content) {
        this.content = content;
    }

    public ServiceReplyAddRequestDto convertToServiceDto() {
        return new ServiceReplyAddRequestDto(content);
    }
}
