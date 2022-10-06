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
    private String userWallet;
    private int amount;

    public WebCheckAmountGetRequestDto(Long artistSeq, String userWallet, int amount) {
        this.artistSeq = artistSeq;
        this.userWallet = userWallet;
        this.amount = amount;
    }

    public ServiceCheckAmountGetRequestDto convertToService() {
        return new ServiceCheckAmountGetRequestDto(artistSeq, userWallet, amount);
    }
}
