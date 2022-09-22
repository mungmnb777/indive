package com.ssafy.indive.domain.music.controller;

import com.ssafy.indive.domain.music.controller.dto.WebMusicAddRequestDto;
import com.ssafy.indive.domain.music.service.MusicAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class MusicController {

    private final MusicAddService musicAddService;

    @PostMapping
    public ResponseEntity<?> addMusic(@Validated @ModelAttribute WebMusicAddRequestDto dto) {
        return new ResponseEntity<>(musicAddService.addMusic(dto.convertToServiceDto()), HttpStatus.OK);
    }
}
