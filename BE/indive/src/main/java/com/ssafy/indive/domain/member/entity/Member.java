package com.ssafy.indive.domain.member.entity;

import com.ssafy.indive.domain.member.entity.enumeration.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Generated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name="member_seq")
    private Long seq;

    @Column(name="member_email")
    private String email;

    @Column(name="member_password")
    private String password;

    @Column(name="member_nickname")
    private String nickname;

    @Column(name="member_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="member_wallet")
    private String wallet;

    @Column(name="member_image_origin")
    private String imageOrigin;

    @Column(name="member_image_uuid")
    private String imageUuid;

    @Column(name="member_background_origin")
    private String backgroundOrigin;

    @Column(name="member_background_uuid")
    private String backgroundUuid;

    @Column(name="member_profile_message")
    private String profileMessage;

    @Column(name="member_notice")
    private String notice;

    @Builder
    public Member(Long seq, String email, String password, String nickname, Role role, String wallet, String imageOrigin, String imageUuid, String backgroundOrigin, String backgroundUuid, String profileMessage, String notice) {
        this.seq = seq;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.wallet = wallet;
        this.imageOrigin = imageOrigin;
        this.imageUuid = imageUuid;
        this.backgroundOrigin = backgroundOrigin;
        this.backgroundUuid = backgroundUuid;
        this.profileMessage = profileMessage;
        this.notice = notice;
    }
}
