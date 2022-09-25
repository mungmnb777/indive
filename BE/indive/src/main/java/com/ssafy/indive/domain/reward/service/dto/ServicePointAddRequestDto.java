package com.ssafy.indive.domain.reward.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicePointAddRequestDto {
    private Long artistSeq;

    private double coin;

    public ServicePointAddRequestDto(Long artistSeq, double coin) {
        this.artistSeq = artistSeq;
        this.coin = coin;
    }
}
