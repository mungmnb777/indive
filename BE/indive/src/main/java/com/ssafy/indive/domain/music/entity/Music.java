package com.ssafy.indive.domain.music.entity;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.music.exception.MusicFileNotFoundException;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicModifyRequestDto;
import com.ssafy.indive.global.utils.FileUtils;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Generated
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

    // TODO: 엔티티가 DTO에 의존하는 관계는 좋아보이지 않는다. 추후에 리팩토링해보자
    public void update(ServiceMusicModifyRequestDto dto) {
        title = dto.getTitle();
        lyricist = dto.getLyricist();
        composer = dto.getComposer();
        genre = dto.getGenre();
        description = dto.getDescription();
        lyrics = dto.getLyrics();
        releaseDatetime = dto.getReleaseDateTime();
        reservationDatetime = dto.getReservationDateTime();
    }

    public void uploadFiles(MultipartFile image, MultipartFile musicFile) {
        // 앨범 커버
        imageOrigin = image == null ? null : image.getOriginalFilename();
        imageUuid = image == null ? null : FileUtils.saveFile(image);

        // mp3 파일이 null일 경우 예외 처리
        if (musicFile == null) throw new MusicFileNotFoundException("음악 파일은 항상 첨부해야 합니다!");

        // mp3 파일
        musicFileOrigin = musicFile.getOriginalFilename();
        musicFileUuid = FileUtils.saveFile(musicFile);
    }
}
