package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.exception.NotMatchMemberException;
import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicModifyRequestDto;
import com.ssafy.indive.global.utils.FileUtils;
import com.ssafy.indive.security.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicDeleteService {

    private final MusicRepository musicRepository;

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
}
