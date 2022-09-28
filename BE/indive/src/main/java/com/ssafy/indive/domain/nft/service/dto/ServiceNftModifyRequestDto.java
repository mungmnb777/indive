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

    private int amount;

    public ServiceNftModifyRequestDto(Long artistSeq, int amount) {
        this.artistSeq = artistSeq;
        this.amount = amount;
    }
}
