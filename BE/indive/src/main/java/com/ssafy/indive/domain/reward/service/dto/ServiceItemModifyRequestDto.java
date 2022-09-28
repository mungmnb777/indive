package com.ssafy.indive.domain.reward.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceItemModifyRequestDto {

    private String content;

    private int point;

    private int stock;

    public ServiceItemModifyRequestDto(String content, int point, int stock) {
        this.content = content;
        this.point = point;
        this.stock = stock;
    }
}
