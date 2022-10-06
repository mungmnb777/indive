package com.ssafy.indive.domain.member.controller;

import com.ssafy.indive.domain.member.controller.dto.WebDuplicatedEmail;
import com.ssafy.indive.domain.member.controller.dto.WebMemberAddRequestDto;
import com.ssafy.indive.domain.member.controller.dto.WebMemberModifyRequestDto;
import com.ssafy.indive.domain.member.controller.dto.WebMemberWriteNoticeRequestDto;
import com.ssafy.indive.domain.member.exception.NotMatchMemberException;
import com.ssafy.indive.domain.member.service.MemberAddService;
import com.ssafy.indive.domain.member.service.MemberBlockchainService;
import com.ssafy.indive.domain.member.service.MemberModifyService;
import com.ssafy.indive.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {


    private final MemberAddService memberAddService;
    private final MemberReadService memberReadService;

    private final MemberModifyService memberModifyService;

    private final MemberBlockchainService memberBlockchainService;

    @PostMapping("join")
    public ResponseEntity<?> addMember(@RequestBody WebMemberAddRequestDto dto) {
        return new ResponseEntity<>(memberAddService.addMember(dto.convertToServiceDto()), HttpStatus.OK);
    }

    @GetMapping("duplicated-email")
    public ResponseEntity<?> isDuplicated(@Validated @ModelAttribute WebDuplicatedEmail dto) {
        System.out.println("duplicated 에 들어옴" + dto.getEmail());
        return new ResponseEntity<>(memberReadService.isDuplicated(dto.convertToServiceDto()), HttpStatus.OK);
    }

    @GetMapping("/{memberSeq}")
    public ResponseEntity<?> getMemberDetails(@PathVariable("memberSeq") long Seq) {
        return new ResponseEntity<>(memberReadService.getMemberDetails(Seq), HttpStatus.OK);
    }

    @PutMapping("/{memberSeq}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> modifyMember(@Validated @ModelAttribute WebMemberModifyRequestDto dto, @PathVariable("memberSeq") long memberSeq) {
        try {
        return new ResponseEntity<>(memberModifyService.modifyMember(memberSeq, dto.convertToServiceDto()), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        } catch (NotMatchMemberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/my-account")
    @Secured("ROLE_USER")
    public ResponseEntity<?> getLoginMemberDetails() {
        return new ResponseEntity<>(memberReadService.getLoginMemberDetails(), HttpStatus.OK);
    }

    @PostMapping("{memberSeq}/notice")
    @Secured("ROLE_USER")
    public ResponseEntity<?> writeNotice(@RequestBody WebMemberWriteNoticeRequestDto dto ,@PathVariable("memberSeq") long memberSeq) {

        return new ResponseEntity<>(memberModifyService.writeNotice( dto.convertToServiceDto(),memberSeq), HttpStatus.OK);
    }

    @GetMapping(value = "/{memberSeq}/profileimg-download", produces = "application/octet-stream")
    public ResponseEntity<?> downloadProfile(@PathVariable("memberSeq") long memberSeq) {
        try {
            return new ResponseEntity<>(memberReadService.downloadProfileImage(memberSeq), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{memberSeq}/backgroundimg-download", produces = "application/octet-stream")
    public ResponseEntity<?> downloadBackground(@PathVariable("memberSeq") long memberSeq) {
        try {
            return new ResponseEntity<>(memberReadService.downloadBackgroundImage(memberSeq), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/donation-rank/{address}")
    public ResponseEntity<?> getDonationRanking(@PathVariable("address") String address){
        return new ResponseEntity<>(memberBlockchainService.getDonationRanking(address), HttpStatus.OK);
    }

}
