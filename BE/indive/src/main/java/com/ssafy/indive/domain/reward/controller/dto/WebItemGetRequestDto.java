package com.ssafy.indive.domain.reward.controller.dto;

import com.ssafy.indive.domain.reward.service.dto.ServiceItemGetRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebItemGetRequestDto {

    private Long artistSeq;

    public WebItemGetRequestDto(Long artistSeq) {
        this.artistSeq = artistSeq;
    }

    public ServiceItemGetRequestDto convertToServiceDto() {
        return new ServiceItemGetRequestDto(artistSeq);
    }
}
