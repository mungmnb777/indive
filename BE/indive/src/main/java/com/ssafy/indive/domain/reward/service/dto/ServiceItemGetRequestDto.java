package com.ssafy.indive.domain.reward.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceItemGetRequestDto {

    private Long artistSeq;

    public ServiceItemGetRequestDto(Long artistSeq) {
        this.artistSeq = artistSeq;
    }
}
