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
public class MusicDeleteService {

    private final MusicRepository musicRepository;

    // TODO : 로그인한 유저가 해당 음원의 주인인지 검증하는 절차가 필요함
    public boolean deleteMusic(long musicSeq) {
        Music findMusic = musicRepository.findById(musicSeq).orElseThrow(IllegalArgumentException::new);

        FileUtils.deleteFile(findMusic.getImageUuid());
        FileUtils.deleteFile(findMusic.getMusicFileUuid());

        musicRepository.delete(findMusic);

        return true;
    }
}
