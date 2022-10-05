package com.ssafy.indive.domain;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.entity.enumeration.Role;
import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.entity.MusicLike;
import com.ssafy.indive.domain.music.entity.Reply;
import com.ssafy.indive.domain.nft.entity.Nft;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class MockEntityFactory {

    public static Member member() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        return Member.builder()
                .seq(1L)
                .email("mungmnb777@gmail.com")
                .password(encoder.encode("password"))
                .nickname("명범짱")
                .notice("공지사항")
                .profileMessage("프로필 상태 메시지")
                .role(Role.ROLE_USER)
                .wallet("지갑")
                .build();
    }

    public static Music music() {
        return Music.builder()
                .title("제목")
                .author(MockEntityFactory.member())
                .composer("작곡가")
                .lyricist("작사가")
                .genre("장르")
                .description("설명")
                .lyrics("가사")
                .releaseDatetime(LocalDateTime.of(2022, 9, 20, 12, 0))
                .reservationDatetime(LocalDateTime.of(2022, 9, 20, 12, 0).plusMinutes(10))
                .build();
    }

    public static MusicLike musicLike() {
        return MusicLike.builder()
                .music(music())
                .liker(member())
                .build();
    }

    public static Reply reply() {
        return Reply.builder()
                .music(music())
                .author(member())
                .content("댓글")
                .build();
    }

    public static Nft nft() {
        return Nft.builder()
                .lowerDonationAmount(1000)
                .stock(100)
                .artist(member())
                .build();
    }


}
