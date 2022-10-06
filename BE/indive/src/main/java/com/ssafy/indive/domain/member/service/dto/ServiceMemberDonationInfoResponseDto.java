package com.ssafy.indive.domain.member.service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceMemberDonationInfoResponseDto {

    private String address;

    private int totalValue;

    @Builder
    public ServiceMemberDonationInfoResponseDto(String address, int totalValue){
        this.address = address;
        this.totalValue = totalValue;
    }
}
