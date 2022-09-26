package com.ssafy.indive.domain.music.controller.dto;

import com.ssafy.indive.domain.music.service.dto.ServiceReplyModifyRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebReplyModifyRequestDto {

    private String content;

    public WebReplyModifyRequestDto(String content) {
        this.content = content;
    }

    public ServiceReplyModifyRequestDto convertToServiceDto() {
        return new ServiceReplyModifyRequestDto(content);
    }
}
