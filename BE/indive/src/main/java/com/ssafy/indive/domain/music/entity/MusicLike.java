package com.ssafy.indive.domain.music.entity;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.music.entity.key.MusicLikeCompositeKey;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@IdClass(MusicLikeCompositeKey.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicLike {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ml_liker_seq")
    private Member liker;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ml_music_seq")
    private Music music;

    @Builder
    public MusicLike(Member liker, Music music) {
        this.liker = liker;
        this.music = music;
    }
}
