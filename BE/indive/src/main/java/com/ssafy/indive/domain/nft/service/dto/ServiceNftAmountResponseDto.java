package com.ssafy.indive.domain.nft.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceNftAmountResponseDto {

    private int amount;

    public ServiceNftAmountResponseDto(int amount) {
        this.amount = amount;
    }
}
