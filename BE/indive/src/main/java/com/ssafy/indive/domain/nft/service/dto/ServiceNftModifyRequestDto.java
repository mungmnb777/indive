package com.ssafy.indive.domain.nft.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceNftModifyRequestDto {

    private Long artistSeq;
    private String wallet;
    private int amount;

    public ServiceNftModifyRequestDto(Long artistSeq, String wallet, int amount) {
        this.artistSeq = artistSeq;
        this.wallet = wallet;
        this.amount = amount;
    }
}
