package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.music.controller.dto.WebMusicGetCondition;
import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.entity.Reply;
import com.ssafy.indive.domain.music.repository.MusicLikeRepository;
import com.ssafy.indive.domain.music.repository.MusicQueryRepository;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicGetResponseDto;
import com.ssafy.indive.domain.music.service.dto.ServiceReplyGetResponseDto;
import com.ssafy.indive.global.utils.FileUtils;
import com.ssafy.indive.security.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MusicReadService {

    private final MusicRepository musicRepository;
    private final MusicQueryRepository musicQueryRepository;

    private final MusicLikeRepository musicLikeRepository;

    // TODO : 테스트 코드 작성해야함
    public List<ServiceMusicGetResponseDto> getMusic(WebMusicGetCondition condition, Pageable pageable) {
        List<Music> music = musicQueryRepository.findAll(condition, pageable);

        List<ServiceMusicGetResponseDto> dtos = new ArrayList<>();

        for (Music m : music) {
            ServiceMusicGetResponseDto dto = ServiceMusicGetResponseDto.builder()
                    .musicSeq(m.getSeq())
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

    public List<ServiceMusicGetResponseDto> getMyMusic(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Member loginMember = principal.getMember();

        List<Music> findMusic = musicQueryRepository.findAllByAuthor(loginMember, pageable);

        List<ServiceMusicGetResponseDto> dtos = new ArrayList<>();

        for (Music m : findMusic) {
            ServiceMusicGetResponseDto dto = ServiceMusicGetResponseDto.builder()
                    .musicSeq(m.getSeq())
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

    public ServiceMusicGetResponseDto getMusicDetails(long musicSeq) {
        Music m = musicRepository.findById(musicSeq).orElseThrow(IllegalArgumentException::new);

        return ServiceMusicGetResponseDto.builder()
                .musicSeq(m.getSeq())
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
    }

    public boolean isLike(long musicSeq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Member loginMember = principal.getMember();

        Music findMusic = musicRepository.findById(musicSeq).orElseThrow(IllegalArgumentException::new);

        musicLikeRepository.findByMusicAndLiker(findMusic, loginMember).orElseThrow(IllegalArgumentException::new);

        return true;
    }

    public int getLikeCount(long musicSeq) {
        Music findMusic = musicRepository.findById(musicSeq).orElseThrow(IllegalArgumentException::new);

        return findMusic.getLikeCount();
    }

    public List<ServiceReplyGetResponseDto> getMusicReply(long musicSeq) {
        Music findMusic = musicRepository.findById(musicSeq).orElseThrow(IllegalArgumentException::new);

        List<Reply> replies = findMusic.getReplies();

        List<ServiceReplyGetResponseDto> dtos = new ArrayList<>();

        for (Reply reply : replies) {
            dtos.add(new ServiceReplyGetResponseDto(reply.getSeq(), reply.getAuthor(), reply.getContent()));
        }

        return dtos;
    }

    public UrlResource downloadMusic(long musicSeq) {
        Music findMusic = musicRepository.findById(musicSeq).orElseThrow(IllegalArgumentException::new);

        return FileUtils.getUrlResource(findMusic.getMusicFileUuid());
    }

    public UrlResource downloadImage(long musicSeq) {
        Music findMusic = musicRepository.findById(musicSeq).orElseThrow(IllegalArgumentException::new);

        return FileUtils.getUrlResource(findMusic.getImageUuid());
    }
}
