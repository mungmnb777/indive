package com.ssafy.indive.domain.member.service.dto;

import com.ssafy.indive.domain.member.entity.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceMemberGeSearchResponseDto {

    private long memberSeq;
    @NotBlank
    private String nickname;


    @Builder
    public ServiceMemberGeSearchResponseDto(long memberSeq, String nickname) {
        this.memberSeq = memberSeq;
        this.nickname = nickname;
    }

    public ServiceMemberGeSearchResponseDto(Member member) {
        this.memberSeq = member.getSeq();
        this.nickname = member.getNickname();
    }
}
