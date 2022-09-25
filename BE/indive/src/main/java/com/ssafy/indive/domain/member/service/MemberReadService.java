package com.ssafy.indive.domain.member.service;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.domain.member.service.dto.ServiceDuplicatedEmail;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberAddRequestDto;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberGetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReadService {

    private final MemberRepository memberRepository;

    public boolean isDuplicated(ServiceDuplicatedEmail convertToServiceDto) {
        System.out.println(convertToServiceDto.getEmail());
        if(memberRepository.existsByEmail(convertToServiceDto.getEmail())) return true;
        else return false;
    }

    public ServiceMemberGetResponseDto getMemberDetails(long seq) {

        Member member = memberRepository.findBySeq(seq).orElseThrow(IllegalArgumentException::new);

        return ServiceMemberGetResponseDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .role(member.getRole())
                .wallet(member.getWallet())
                .profileMessage(member.getProfileMessage())
                .notice(member.getNotice())
                .build();
    }
}
