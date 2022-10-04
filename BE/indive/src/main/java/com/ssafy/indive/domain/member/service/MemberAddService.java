package com.ssafy.indive.domain.member.service;

import com.ssafy.indive.domain.member.controller.dto.WebMemberWriteNoticeRequestDto;
import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.entity.enumeration.Role;
import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.domain.member.service.dto.ServiceDuplicatedEmail;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberAddRequestDto;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberWriteNoticeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberAddService {

    private final MemberRepository memberRepository;

    private final MemberReadService memberReadService;

    public boolean addMember(ServiceMemberAddRequestDto dto) {

        if(!memberReadService.isDuplicated(new ServiceDuplicatedEmail(dto.getEmail()))){
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            Member member = Member.builder()
                    .email(dto.getEmail())
                    .password(encoder.encode(dto.getPassword()))
                    .nickname(dto.getNickname())
                    .wallet(dto.getWallet())
                    .profileMessage(dto.getProfileMessage())
                    //기본 ROLE 넣기
                    .role(Role.ROLE_USER)
                    //기본 이미지 추가
                    .imageOrigin("default_profile.png")
                    .imageUuid("default_profile.png")
                    .backgroundOrigin("default_background.png")
                    .backgroundUuid("default_background.png")
                    .build();

            memberRepository.save(member);

            return true;

        }
        return false;
    }




}
