package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicAddRequestDto;
import com.ssafy.indive.security.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicAddService {

    private final MusicRepository musicRepository;

    public boolean addMusic(ServiceMusicAddRequestDto dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Member loginMember = principal.getMember();

        Music music = Music.builder()
                .author(loginMember)
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
