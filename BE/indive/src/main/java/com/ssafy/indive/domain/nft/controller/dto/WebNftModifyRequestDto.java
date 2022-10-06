package com.ssafy.indive.domain.nft.controller.dto;

import com.ssafy.indive.domain.nft.service.dto.ServiceCheckAmountGetRequestDto;
import com.ssafy.indive.domain.nft.service.dto.ServiceNftModifyRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebNftModifyRequestDto {

    private Long artistSeq;
    private String wallet;
    private int amount;

    public WebNftModifyRequestDto(Long artistSeq, String wallet, int amount) {
        this.artistSeq = artistSeq;
        this.wallet = wallet;
        this.amount = amount;
    }

    public ServiceNftModifyRequestDto convertToService() {
        return new ServiceNftModifyRequestDto(artistSeq, wallet, amount);
    }
}
