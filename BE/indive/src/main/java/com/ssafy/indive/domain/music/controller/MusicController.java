package com.ssafy.indive.domain.music.controller;

import com.ssafy.indive.domain.member.exception.NotMatchMemberException;
import com.ssafy.indive.domain.music.controller.dto.*;
import com.ssafy.indive.domain.music.exception.MusicFileNotFoundException;
import com.ssafy.indive.domain.music.service.MusicAddService;
import com.ssafy.indive.domain.music.service.MusicDeleteService;
import com.ssafy.indive.domain.music.service.MusicModifyService;
import com.ssafy.indive.domain.music.service.MusicReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    @Secured("ROLE_USER")
    public ResponseEntity<?> addMusic(@Validated @ModelAttribute WebMusicAddRequestDto dto) {
        try {
            return new ResponseEntity<>(musicAddService.addMusic(dto.convertToServiceDto()), HttpStatus.OK);
        } catch (MusicFileNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{musicSeq}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured("ROLE_USER")
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
    @Secured("ROLE_USER")
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
    public ResponseEntity<?> getMusic(@ModelAttribute WebMusicGetCondition condition, Pageable pageable) {
        return new ResponseEntity<>(musicReadService.getMusic(condition, pageable), HttpStatus.OK);
    }

    @GetMapping("/my-music")
    @Secured("ROLE_USER")
    public ResponseEntity<?> getMyMusic(Pageable pageable) {
        return new ResponseEntity<>(musicReadService.getMyMusic(pageable), HttpStatus.OK);
    }

    @GetMapping("/{musicSeq}")
    public ResponseEntity<?> getMusicDetails(@PathVariable("musicSeq") long musicSeq) {
        return new ResponseEntity<>(musicReadService.getMusicDetails(musicSeq), HttpStatus.OK);
    }

    @PostMapping("/{musicSeq}/like")
    @Secured("ROLE_USER")
    public ResponseEntity<?> likeMusic(@PathVariable("musicSeq") long musicSeq) {
        try {
            return new ResponseEntity<>(musicAddService.likeMusic(musicSeq), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>("이미 좋아요를 누른 회원입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{musicSeq}/like")
    @Secured("ROLE_USER")
    public ResponseEntity<?> isLike(@PathVariable("musicSeq") long musicSeq) {
        try {
            return new ResponseEntity<>(musicReadService.isLike(musicSeq), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.OK);
        }
    }

    @DeleteMapping("/{musicSeq}/like")
    @Secured("ROLE_USER")
    public ResponseEntity<?> deleteLike(@PathVariable("musicSeq") long musicSeq) {
        try {
            return new ResponseEntity<>(musicDeleteService.deleteLike(musicSeq), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>("아직 좋아요를 누르지 않은 회원입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{musicSeq}/like/count")
    public ResponseEntity<?> getLikeCount(@PathVariable("musicSeq") long musicSeq) {
        try {
            return new ResponseEntity<>(musicReadService.getLikeCount(musicSeq), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{musicSeq}/reply")
    public ResponseEntity<?> addMusicReply(@PathVariable("musicSeq") long musicSeq, @RequestBody WebReplyAddRequestDto dto) {
        try {
            return new ResponseEntity<>(musicAddService.addMusicReply(musicSeq, dto.convertToServiceDto()), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{musicSeq}/reply")
    @Secured("ROLE_USER")
    public ResponseEntity<?> getMusicReply(@PathVariable("musicSeq") long musicSeq) {
        try {
            return new ResponseEntity<>(musicReadService.getMusicReply(musicSeq), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{musicSeq}/reply/{replySeq}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> modifyMusicReply(@PathVariable("replySeq") long replySeq,
                                              @RequestBody WebReplyModifyRequestDto dto) {
        try {
            return new ResponseEntity<>(musicModifyService.modifyMusicReply(replySeq, dto.convertToServiceDto()), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        } catch (NotMatchMemberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{musicSeq}/reply/{replySeq}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> deleteMusicReply(@PathVariable("replySeq") long replySeq) {
        try {
            return new ResponseEntity<>(musicDeleteService.deleteMusicReply(replySeq), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        } catch (NotMatchMemberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{musicSeq}/file-download", produces = "application/octet-stream")
    public ResponseEntity<?> downloadMusic(@PathVariable("musicSeq") long musicSeq) {
        try {
            return new ResponseEntity<>(musicReadService.downloadMusic(musicSeq), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{musicSeq}/image-download", produces = "application/octet-stream")
    @Secured({"ROLE_USER", "ROLE_ANONYMOUS"})
    public ResponseEntity<?> downloadImage(@PathVariable("musicSeq") long musicSeq) {
        try {
            return new ResponseEntity<>(musicReadService.downloadImage(musicSeq), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
    }
}
