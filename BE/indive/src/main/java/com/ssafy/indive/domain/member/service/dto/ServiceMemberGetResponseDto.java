package com.ssafy.indive.domain.member.service.dto;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.entity.enumeration.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceMemberGetResponseDto {


    private long memberSeq;

    @NotBlank
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private Role role;

    private String wallet;


    private String profileMessage;


    private String notice;


    @Builder

    public ServiceMemberGetResponseDto(long memberSeq, String email, String nickname, Role role, String wallet, String profileMessage, String notice) {
        this.memberSeq = memberSeq;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.wallet = wallet;
        this.profileMessage = profileMessage;
        this.notice = notice;
    }

    public ServiceMemberGetResponseDto(Member member) {
        this.memberSeq = member.getSeq();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.role = member.getRole();
        this.wallet = member.getWallet();
        this.profileMessage = member.getProfileMessage();
        this.notice = member.getNotice();
    }
}
