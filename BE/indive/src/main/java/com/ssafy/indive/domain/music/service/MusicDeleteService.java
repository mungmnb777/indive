package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.exception.NotMatchMemberException;
import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.entity.MusicLike;
import com.ssafy.indive.domain.music.entity.Reply;
import com.ssafy.indive.domain.music.repository.MusicLikeRepository;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.repository.ReplyRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicModifyRequestDto;
import com.ssafy.indive.global.utils.FileUtils;
import com.ssafy.indive.security.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicDeleteService {

    private final MusicRepository musicRepository;

    private final MusicLikeRepository musicLikeRepository;

    private final ReplyRepository replyRepository;

    public boolean deleteMusic(long musicSeq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Member loginMember = principal.getMember();

        Music findMusic = musicRepository.findById(musicSeq).orElseThrow(IllegalArgumentException::new);

        if (Objects.equals(loginMember.getSeq(), findMusic.getAuthor().getSeq()))
            throw new NotMatchMemberException("해당 음원의 주인이 아닙니다.");

        FileUtils.deleteFile(findMusic.getImageUuid());
        FileUtils.deleteFile(findMusic.getMusicFileUuid());

        musicRepository.delete(findMusic);

        return true;
    }

    public boolean deleteLike(long musicSeq) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Member loginMember = principal.getMember();

        Music findMusic = musicRepository.findById(musicSeq).orElseThrow(IllegalArgumentException::new);

        Optional<MusicLike> optionalMusicLike = musicLikeRepository.findByMusicAndLiker(findMusic, loginMember);

        // 만약 이미 좋아요가 없는 회원일 경우 바로 리턴해야 한다.
        if (!optionalMusicLike.isPresent()) throw new IllegalStateException();

        musicLikeRepository.delete(optionalMusicLike.get());

        findMusic.minusLikeCount();

        return true;
    }

    public boolean deleteMusicReply(long replySeq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Member loginMember = principal.getMember();

        Reply findReply = replyRepository.findById(replySeq).orElseThrow(IllegalArgumentException::new);

        if (!Objects.equals(loginMember.getSeq(), findReply.getAuthor().getSeq()))
            throw new NotMatchMemberException("해당 댓글의 주인이 아닙니다.");

        replyRepository.delete(findReply);

        return true;
    }
}
