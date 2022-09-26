package com.ssafy.indive.domain.member.controller;

import com.ssafy.indive.domain.member.controller.dto.WebDuplicatedEmail;
import com.ssafy.indive.domain.member.controller.dto.WebMemberAddRequestDto;
import com.ssafy.indive.domain.member.service.MemberAddService;
import com.ssafy.indive.domain.member.service.MemberReadService;
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
    private final MemberReadService memberReadService;

    @PostMapping("join")
    public ResponseEntity<?> addMember(@Validated @RequestBody WebMemberAddRequestDto dto) {
        return new ResponseEntity<>(memberAddService.addMember(dto.convertToServiceDto()), HttpStatus.OK);
    }

    @GetMapping("duplicated-email")
    public ResponseEntity<?> isDuplicated(@Validated @ModelAttribute WebDuplicatedEmail dto) {
        return new ResponseEntity<>(memberReadService.isDuplicated(dto.convertToServiceDto()), HttpStatus.OK);
    }

    @GetMapping("/{memberSeq}")
    public ResponseEntity<?> getMemberDetails(@PathVariable("memberSeq") long Seq) {
        System.out.println("들어옴");
        return new ResponseEntity<>(memberReadService.getMemberDetails(Seq), HttpStatus.OK);
    }


// TODO : 1. /members/login 으로 보내야 함. SecurityConfig 에 설정을 해 주면 된다.
// TODO : 2. 로그인은 DTO 로 받아야 한다. JwtAuthenticationFilter에서 Member 로 받고 있는데 이거 dto 로 받아라. 냅다 바꾸면 된다.
}
