package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicModifyRequestDto;
import com.ssafy.indive.global.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicModifyService {

    private final MusicRepository musicRepository;

    // TODO : 로그인한 유저가 해당 음원의 주인인지 검증하는 절차가 필요함
    public boolean modifyMusic(long musicSeq, ServiceMusicModifyRequestDto dto) {
        Music findMusic = musicRepository.findById(musicSeq).orElseThrow(IllegalArgumentException::new);

        // 파일 관련 필드 제외한 필드 수정
        findMusic.update(dto);

        // 이전 파일 삭제
        FileUtils.deleteFile(findMusic.getImageUuid());
        FileUtils.deleteFile(findMusic.getMusicFileUuid());

        // 새로운 파일 저장 및 파일 정보 입력
        findMusic.uploadFiles(dto.getImage(), dto.getMusicFile());

        return true;
    }
}
