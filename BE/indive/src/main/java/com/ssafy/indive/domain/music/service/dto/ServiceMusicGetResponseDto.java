package com.ssafy.indive.domain.music.service.dto;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.global.utils.DateTimeUtils;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceMusicGetResponseDto {

    private Long seq;

    private Member artist;

    private String title;

    private String lyricist;

    private String composer;

    private String genre;

    private String description;

    private String lyrics;

    private String releaseDateTime;

    private String reservationDateTime;

    private String createDate;

    private String updateDate;

    private int likeCount;

    @Builder
    public ServiceMusicGetResponseDto(Long seq, Member artist, String title, String lyricist, String composer, String genre, String description, String lyrics, LocalDateTime releaseDateTime, LocalDateTime reservationDateTime, LocalDateTime createDate, LocalDateTime updateDate, int likeCount) {
        this.seq = seq;
        this.artist = artist;
        this.title = title;
        this.lyricist = lyricist;
        this.composer = composer;
        this.genre = genre;
        this.description = description;
        this.lyrics = lyrics;
        this.releaseDateTime = DateTimeUtils.format(releaseDateTime);
        this.reservationDateTime = DateTimeUtils.format(reservationDateTime);
        this.createDate = DateTimeUtils.format(createDate);
        this.updateDate = DateTimeUtils.format(updateDate);
        this.likeCount = likeCount;
    }
}
