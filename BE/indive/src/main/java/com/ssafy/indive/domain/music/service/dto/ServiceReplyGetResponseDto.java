package com.ssafy.indive.domain.music.service.dto;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberGetResponseDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceReplyGetResponseDto {

    private long replySeq;

    private ServiceMemberGetResponseDto member;

    private String content;

    public ServiceReplyGetResponseDto(long replySeq, Member member, String content) {
        this.replySeq = replySeq;
        this.member = new ServiceMemberGetResponseDto(member);
        this.content = content;
    }
}
