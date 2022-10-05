package com.ssafy.indive.domain.member.service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebMemberGetCondition {

    private String nickname;

    @Builder WebMemberGetCondition(String nickname){
        this.nickname = nickname;
    }

    public String convertToString(){
        return this.nickname;
    }

}
