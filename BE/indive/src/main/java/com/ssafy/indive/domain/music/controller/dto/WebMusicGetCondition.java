package com.ssafy.indive.domain.music.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebMusicGetCondition {

    private Long artistSeq;

    private String title;

    private String artist;

    private String sort;

    private String genre;

    @Builder
    public WebMusicGetCondition(Long artistSeq, String title, String artist, String sort, String genre) {
        this.artistSeq = artistSeq;
        this.title = title;
        this.artist = artist;
        this.sort = sort;
        this.genre = genre;
    }
}
