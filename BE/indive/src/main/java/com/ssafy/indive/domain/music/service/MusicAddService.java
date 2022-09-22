package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.exception.MusicFileNotFoundException;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicAddRequestDto;
import com.ssafy.indive.global.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicAddService {

    private final MusicRepository musicRepository;

    // TODO: 멤버 정보가 현재 null이 들어갑니다. 추후에 시큐리티가 구현되고 추가할 예정입니다.
    public boolean addMusic(ServiceMusicAddRequestDto dto) {
        MultipartFile image = dto.getImage();
        MultipartFile musicFile = dto.getMusicFile();

        // 앨범 커버
        String imageOrigin = image == null ? null : image.getOriginalFilename();
        String imageUuid = image == null ? null : FileUtils.saveFile(image);

        // mp3 파일이 null일 경우 예외 처리
        if (musicFile == null) throw new MusicFileNotFoundException("음악 파일은 항상 첨부해야 합니다!");

        // mp3 파일
        String musicFileOrigin = musicFile.getOriginalFilename();
        String musicFileUuid = FileUtils.saveFile(musicFile);

        Music music = Music.builder()
                .author(null)
                .title(dto.getTitle())
                .lyricist(dto.getLyricist())
                .composer(dto.getComposer())
                .genre(dto.getGenre())
                .description(dto.getDescription())
                .lyrics(dto.getLyrics())
                .releaseDatetime(dto.getReleaseDateTime())
                .reservationDatetime(dto.getReservationDateTime())
                .imageOrigin(imageOrigin)
                .imageUuid(imageUuid)
                .musicFileOrigin(musicFileOrigin)
                .musicFileUuid(musicFileUuid)
                .build();

        musicRepository.save(music);

        return true;
    }
}
