package com.ssafy.indive.domain.music.controller.dto;

import com.ssafy.indive.domain.music.service.dto.ServiceMusicAddRequestDto;
import com.ssafy.indive.global.constant.DatetimeConst;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebMusicAddRequestDto {

    @NotBlank
    private String title;

    private String lyricist;

    @NotBlank
    private String composer;

    @NotBlank
    private String genre;

    @NotBlank
    private String description;

    private String lyrics;

    @DateTimeFormat(pattern = DatetimeConst.FORMAT)
    private LocalDateTime releaseDateTime;

    @DateTimeFormat(pattern = DatetimeConst.FORMAT)
    private LocalDateTime reservationDateTime;

    private MultipartFile image;

    private MultipartFile musicFile;

    @Builder
    public WebMusicAddRequestDto(String title, String lyricist, String composer, String genre, String description, String lyrics, LocalDateTime releaseDateTime, LocalDateTime reservationDateTime, MultipartFile image, MultipartFile musicFile) {
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

    public ServiceMusicAddRequestDto convertToServiceDto() {
        return ServiceMusicAddRequestDto.builder()
                .title(title)
                .lyricist(lyricist)
                .composer(composer)
                .genre(genre)
                .description(description)
                .lyrics(lyrics)
                .releaseDateTime(releaseDateTime)
                .reservationDateTime(reservationDateTime)
                .image(image)
                .musicFile(musicFile)
                .build();
    }
}
