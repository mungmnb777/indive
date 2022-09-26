package com.ssafy.indive.domain.reward.controller.dto;

import com.ssafy.indive.domain.reward.service.dto.ServiceItemModifyRequestDto;
import com.ssafy.indive.domain.reward.service.dto.ServicePointAddRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebItemModifyRequestDto {

    private String content;

    private int point;

    private int stock;

    public WebItemModifyRequestDto(String content, int point, int stock) {
        this.content = content;
        this.point = point;
        this.stock = stock;
    }

    public ServiceItemModifyRequestDto convertToServiceDto() {
        return new ServiceItemModifyRequestDto(content, point, stock);
    }
}
