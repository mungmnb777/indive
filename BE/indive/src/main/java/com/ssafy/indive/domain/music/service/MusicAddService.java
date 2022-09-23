package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicAddRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicAddService {

    private final MusicRepository musicRepository;

    // TODO: 멤버 정보가 현재 null이 들어갑니다. 추후에 시큐리티가 구현되고 추가할 예정입니다.
    public boolean addMusic(ServiceMusicAddRequestDto dto) {

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
                .likeCount(0)
                .build();

        music.uploadFiles(dto.getImage(), dto.getMusicFile());

        musicRepository.save(music);

        return true;
    }
}
