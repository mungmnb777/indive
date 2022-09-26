package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.entity.MusicLike;
import com.ssafy.indive.domain.music.repository.MusicLikeRepository;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicAddRequestDto;
import com.ssafy.indive.security.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicAddService {

    private final MusicRepository musicRepository;

    private final MusicLikeRepository musicLikeRepository;

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

    public boolean likeMusic(long musicSeq) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Member loginMember = principal.getMember();

        Music findMusic = musicRepository.findById(musicSeq).orElseThrow(IllegalArgumentException::new);

        MusicLike like = MusicLike.builder()
                .music(findMusic)
                .liker(loginMember)
                .build();

        Optional<MusicLike> optionalMusicLike = musicLikeRepository.findByMusicAndLiker(findMusic, loginMember);

        // musicLike가 있으면 이미 좋아요를 누른 것이므로 바로 리턴해야한다.
        if (optionalMusicLike.isPresent()) throw new IllegalStateException();

        musicLikeRepository.save(like);

        findMusic.plusLikeCount();

        return true;
    }
}
