package com.ssafy.indive.domain.box.entity;

import com.ssafy.indive.domain.box.entity.key.BoxMusicCompositeKey;
import com.ssafy.indive.domain.music.entity.Music;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Generated
@IdClass(BoxMusicCompositeKey.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoxMusic {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bm_box_seq")
    private Box box;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bm_music_seq")
    private Music music;

    @Builder
    public BoxMusic(Box box, Music music) {
        this.box = box;
        this.music = music;
    }
}
