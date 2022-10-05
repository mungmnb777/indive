package com.ssafy.indive.domain.member.service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceMemberDonationRankResponseDto {

    private long memberSeq;

    private String nickname;

    private int totalValue;

    @Builder
    public ServiceMemberDonationRankResponseDto(long memberSeq, String nickname, int totalValue){
        this.memberSeq = memberSeq;
        this.nickname = nickname;
        this.totalValue = totalValue;
    }
}
