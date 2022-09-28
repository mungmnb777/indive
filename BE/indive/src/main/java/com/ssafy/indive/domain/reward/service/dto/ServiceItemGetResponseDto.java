package com.ssafy.indive.domain.reward.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.indive.domain.member.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceItemGetResponseDto {

    private Long seq;

    // TODO : 추후에 JsonIgnore 지우고 엔티티를 DTO로 변경
    @JsonIgnore
    private Member artist;

    private String title;

    private String content;

    private int point;

    private int stock;

    @Builder
    public ServiceItemGetResponseDto(Long seq, Member artist, String title, String content, int point, int stock) {
        this.seq = seq;
        this.artist = artist;
        this.title = title;
        this.content = content;
        this.point = point;
        this.stock = stock;
    }
}
