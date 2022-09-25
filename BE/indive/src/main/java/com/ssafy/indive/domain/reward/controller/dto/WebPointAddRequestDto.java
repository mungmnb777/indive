package com.ssafy.indive.domain.reward.controller.dto;

import com.ssafy.indive.domain.reward.service.dto.ServicePointAddRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebPointAddRequestDto {

    private Long artistSeq;

    private double coin;

    public WebPointAddRequestDto(Long artistSeq, double coin) {
        this.artistSeq = artistSeq;
        this.coin = coin;
    }

    public ServicePointAddRequestDto convertToServiceDto() {
        return new ServicePointAddRequestDto(artistSeq, coin);
    }
}
