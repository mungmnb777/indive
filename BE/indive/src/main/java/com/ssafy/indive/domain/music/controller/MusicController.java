package com.ssafy.indive.domain.music.controller;

import com.ssafy.indive.domain.member.exception.NotMatchMemberException;
import com.ssafy.indive.domain.music.controller.dto.WebMusicAddRequestDto;
import com.ssafy.indive.domain.music.controller.dto.WebMusicGetCondition;
import com.ssafy.indive.domain.music.controller.dto.WebMusicModifyRequestDto;
import com.ssafy.indive.domain.music.service.MusicAddService;
import com.ssafy.indive.domain.music.service.MusicDeleteService;
import com.ssafy.indive.domain.music.service.MusicModifyService;
import com.ssafy.indive.domain.music.service.MusicReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class MusicController {

    private final MusicAddService musicAddService;

    private final MusicModifyService musicModifyService;

    private final MusicDeleteService musicDeleteService;

    private final MusicReadService musicReadService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addMusic(@Validated @ModelAttribute WebMusicAddRequestDto dto) {
        return new ResponseEntity<>(musicAddService.addMusic(dto.convertToServiceDto()), HttpStatus.OK);
    }

    @PutMapping(value = "/{musicSeq}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> modifyMusic(@Validated @ModelAttribute WebMusicModifyRequestDto dto, @PathVariable("musicSeq") long musicSeq) {
        try {
            return new ResponseEntity<>(musicModifyService.modifyMusic(musicSeq, dto.convertToServiceDto()), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        } catch (NotMatchMemberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{musicSeq}")
    public ResponseEntity<?> deleteMusic(@PathVariable("musicSeq") long musicSeq) {
        try {
            return new ResponseEntity<>(musicDeleteService.deleteMusic(musicSeq), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        } catch (NotMatchMemberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // TODO : 테스트 코드 작성 해야함
    @GetMapping
    public ResponseEntity<?> getMusic(@ModelAttribute WebMusicGetCondition condition) {
        return new ResponseEntity<>(musicReadService.getMusic(condition), HttpStatus.OK);
    }

    @GetMapping("/{musicSeq}")
    public ResponseEntity<?> getMusicDetails(@PathVariable("musicSeq") long musicSeq) {
        return new ResponseEntity<>(musicReadService.getMusicDetails(musicSeq), HttpStatus.OK);
    }
}
