package com.ssafy.indive.domain.reward.controller.dto;

import com.ssafy.indive.domain.reward.service.dto.ServiceItemAddRequestDto;
import com.ssafy.indive.domain.reward.service.dto.ServicePointAddRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebItemAddRequestDto {

    private String title;

    private String content;

    private int point;

    private int stock;

    public ServiceItemAddRequestDto convertToServiceDto() {
        return new ServiceItemAddRequestDto(title, content, point, stock);
    }
}
