package com.ssafy.indive.domain.music.entity;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.music.service.dto.ServiceReplyModifyRequestDto;
import com.ssafy.indive.global.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Generated
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseEntity {

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
        setMusic(music);
        this.content = content;
    }

    public void setMusic(Music music){
        this.music = music;
        music.getReplies().add(this);
    }

    public void update(ServiceReplyModifyRequestDto dto) {
        content = dto.getContent();
    }
}
