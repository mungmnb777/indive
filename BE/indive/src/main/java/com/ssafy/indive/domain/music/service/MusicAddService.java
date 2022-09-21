package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.music.entity.Music;
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

        // 파일 실제 이름
        String imageOrigin = image.getOriginalFilename();
        String musicFileOrigin = musicFile.getOriginalFilename();

        // 파일 저장된 이름 (이 때 파일을 파일 시스템에 저장한다.)
        String imageUuid = FileUtils.saveFile(image);
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
