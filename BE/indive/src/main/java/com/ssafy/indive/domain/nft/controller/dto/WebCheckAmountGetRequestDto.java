package com.ssafy.indive.domain.nft.controller.dto;

import com.ssafy.indive.domain.nft.service.dto.ServiceCheckAmountGetRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebCheckAmountGetRequestDto {

    private Long artistSeq;

    private int amount;

    public WebCheckAmountGetRequestDto(Long artistSeq, int amount) {
        this.artistSeq = artistSeq;
        this.amount = amount;
    }

    public ServiceCheckAmountGetRequestDto convertToService() {
        return new ServiceCheckAmountGetRequestDto(artistSeq, amount);
    }
}
