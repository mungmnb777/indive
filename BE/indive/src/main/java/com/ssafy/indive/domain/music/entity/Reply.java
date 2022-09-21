package com.ssafy.indive.domain.music.entity;

import com.ssafy.indive.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Generated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {

    @Id
    @GeneratedValue
    @Column(name="reply_seq")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_author_seq")
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_music_seq")
    private Music music;

    @Column(name = "reply_content")
    private String content;

    @Builder
    public Reply(Long seq, Member author, Music music, String content) {
        this.seq = seq;
        this.author = author;
        this.music = music;
        this.content = content;
    }
}
