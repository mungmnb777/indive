package com.ssafy.indive.domain.music.service.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceMusicModifyRequestDto {

    private String title;

    private String lyricist;

    private String composer;

    private String genre;

    private String description;

    private String lyrics;

    private LocalDateTime releaseDateTime;

    private LocalDateTime reservationDateTime;

    private MultipartFile image;

    private MultipartFile musicFile;

    @Builder
    public ServiceMusicModifyRequestDto(String title, String lyricist, String composer, String genre, String description, String lyrics, LocalDateTime releaseDateTime, LocalDateTime reservationDateTime, MultipartFile image, MultipartFile musicFile) {
        this.title = title;
        this.lyricist = lyricist;
        this.composer = composer;
        this.genre = genre;
        this.description = description;
        this.lyrics = lyrics;
        this.releaseDateTime = releaseDateTime;
        this.reservationDateTime = reservationDateTime;
        this.image = image;
        this.musicFile = musicFile;
    }
}
