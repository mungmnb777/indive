package com.ssafy.indive.domain.member.controller;

import com.ssafy.indive.domain.member.controller.dto.WebMemberAddRequestDto;
import com.ssafy.indive.domain.member.service.MemberAddService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {


    private final MemberAddService memberAddService;

    @PostMapping("join")
    public ResponseEntity<?> addMember(@Validated @ModelAttribute WebMemberAddRequestDto dto) {
        return new ResponseEntity<>(memberAddService.addMember(dto.convertToServiceDto()), HttpStatus.OK);
    }

}
