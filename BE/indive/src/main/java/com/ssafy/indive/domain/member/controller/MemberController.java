package com.ssafy.indive.domain.member.controller;

import com.ssafy.indive.domain.member.controller.dto.WebDuplicatedEmail;
import com.ssafy.indive.domain.member.controller.dto.WebMemberAddRequestDto;
import com.ssafy.indive.domain.member.controller.dto.WebMemberModifyRequestDto;
import com.ssafy.indive.domain.member.service.MemberAddService;
import com.ssafy.indive.domain.member.service.MemberModifyService;
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

    private final MemberModifyService memberModifyService;

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
        return new ResponseEntity<>(memberReadService.getMemberDetails(Seq), HttpStatus.OK);
    }

    @PutMapping("/{memberSeq}")
    public ResponseEntity<?> modifyMember(@Validated @ModelAttribute WebMemberModifyRequestDto dto, @PathVariable("memberSeq") long memberSeq) {
        return new ResponseEntity<>(memberModifyService.modifyMember(memberSeq, dto.convertToServiceDto()), HttpStatus.OK);
    }

}
