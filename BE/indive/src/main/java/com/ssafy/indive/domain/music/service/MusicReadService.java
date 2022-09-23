package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.music.controller.dto.WebMusicGetCondition;
import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.repository.MusicQueryRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicGetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicReadService {

    private final MusicQueryRepository musicQueryRepository;

    // TODO : 테스트 코드 작성해야함
    public List<ServiceMusicGetResponseDto> getMusic(WebMusicGetCondition condition) {
        List<Music> music = musicQueryRepository.findAll(condition);

        List<ServiceMusicGetResponseDto> dtos = new ArrayList<>();

        for (Music m : music) {
            ServiceMusicGetResponseDto dto = ServiceMusicGetResponseDto.builder()
                    .seq(m.getSeq())
                    .title(m.getTitle())
                    .artist(m.getAuthor())
                    .composer(m.getComposer())
                    .lyricist(m.getLyricist())
                    .description(m.getDescription())
                    .genre(m.getGenre())
                    .lyrics(m.getLyrics())
                    .releaseDateTime(m.getReleaseDatetime())
                    .reservationDateTime(m.getReservationDatetime())
                    .createDate(m.getCreateDate())
                    .updateDate(m.getUpdateDate())
                    .likeCount(m.getLikeCount())
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }
}
