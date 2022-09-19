package com.ssafy.indive.domain.music.entity;

import com.ssafy.indive.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Music {

    @Id
    @GeneratedValue
    @Column(name="music_seq")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="music_author_seq")
    private Member author;

    @Column(name="music_title")
    private String title;

    @Column(name="music_lyricist")
    private String lyricist;

    @Column(name="music_composer")
    private String composer;

    @Column(name="music_genre")
    private String genre;

    @Column(name="music_description")
    private String description;

    @Column(name="music_lyrics")
    private String lyrics;

    @Column(name="music_release_datetime")
    private LocalDateTime releaseDatetime;

    @Column(name="music_reservation_datetime")
    private LocalDateTime reservationDatetime;

    @Column(name="music_image_origin")
    private String imageOrigin;

    @Column(name="music_image_uuid")
    private String imageUuid;

    @Column(name="music_file_origin")
    private String musicFileOrigin;

    @Column(name="music_file_uuid")
    private String musicFileUuid;

    @Builder
    public Music(Long seq, Member author, String title, String lyricist, String composer, String genre, String description, String lyrics, LocalDateTime releaseDatetime, LocalDateTime reservationDatetime, String imageOrigin, String imageUuid, String musicFileOrigin, String musicFileUuid) {
        this.seq = seq;
        this.author = author;
        this.title = title;
        this.lyricist = lyricist;
        this.composer = composer;
        this.genre = genre;
        this.description = description;
        this.lyrics = lyrics;
        this.releaseDatetime = releaseDatetime;
        this.reservationDatetime = reservationDatetime;
        this.imageOrigin = imageOrigin;
        this.imageUuid = imageUuid;
        this.musicFileOrigin = musicFileOrigin;
        this.musicFileUuid = musicFileUuid;
    }
}
