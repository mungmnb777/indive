package com.ssafy.indive.domain.music.controller.dto;

import com.ssafy.indive.domain.music.service.dto.ServiceMusicAddRequestDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
