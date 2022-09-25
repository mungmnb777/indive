package com.ssafy.indive.domain.reward.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceItemAddRequestDto {
    private String title;

    private String content;

    private int point;

    private int stock;

    public ServiceItemAddRequestDto(String title, String content, int point, int stock) {
        this.title = title;
        this.content = content;
        this.point = point;
        this.stock = stock;
    }
}
