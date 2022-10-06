package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.exception.NotMatchMemberException;
import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.entity.Reply;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.repository.ReplyRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicModifyRequestDto;
import com.ssafy.indive.domain.music.service.dto.ServiceReplyModifyRequestDto;
import com.ssafy.indive.global.utils.FileUtils;
import com.ssafy.indive.security.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicModifyService {

    private final MusicRepository musicRepository;

    private final ReplyRepository replyRepository;

    public boolean modifyMusic(long musicSeq, ServiceMusicModifyRequestDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Member loginMember = principal.getMember();

        Music findMusic = musicRepository.findById(musicSeq).orElseThrow(IllegalArgumentException::new);

        if (!Objects.equals(loginMember.getSeq(), findMusic.getAuthor().getSeq()))
            throw new NotMatchMemberException("해당 음원의 주인이 아닙니다.");

        // 파일 관련 필드 제외한 필드 수정
        findMusic.update(dto);

        // 이전 파일 삭제
        if (StringUtils.hasText(findMusic.getImageOrigin())) FileUtils.deleteFile(findMusic.getImageUuid());
        FileUtils.deleteFile(findMusic.getMusicFileUuid());

        // 새로운 파일 저장 및 파일 정보 입력
        findMusic.uploadFiles(dto.getImage(), dto.getMusicFile());

        return true;
    }

    public boolean modifyMusicReply(long replySeq, ServiceReplyModifyRequestDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Member loginMember = principal.getMember();

        Reply findReply = replyRepository.findById(replySeq).orElseThrow(IllegalArgumentException::new);

        if (!Objects.equals(loginMember.getSeq(), findReply.getAuthor().getSeq()))
            throw new NotMatchMemberException("해당 댓글의 주인이 아닙니다.");

        findReply.update(dto);

        return true;
    }
}
