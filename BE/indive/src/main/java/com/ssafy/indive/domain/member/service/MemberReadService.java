package com.ssafy.indive.domain.member.service;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.domain.member.service.dto.ServiceDuplicatedEmail;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberAddRequestDto;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberGetResponseDto;
import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.global.utils.FileUtils;
import com.ssafy.indive.security.config.auth.PrincipalDetails;
import com.ssafy.indive.security.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReadService {

    private final MemberRepository memberRepository;


    public boolean isDuplicated(ServiceDuplicatedEmail dto) {
        System.out.println("isDuplicated S : "+dto.getEmail());
        System.out.println("isDuplicated S : "+ memberRepository.existsByEmail(dto.getEmail()));
        if(memberRepository.existsByEmail(dto.getEmail())) return true;
        else return false;
    }

    public ServiceMemberGetResponseDto getMemberDetails(long seq) {

        Member member = memberRepository.findBySeq(seq).orElseThrow(IllegalArgumentException::new);

        return ServiceMemberGetResponseDto.builder()
                .memberSeq(member.getSeq())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .role(member.getRole())
                .wallet(member.getWallet())
                .profileMessage(member.getProfileMessage())
                .notice(member.getNotice())
                .build();
    }

    public  ServiceMemberGetResponseDto getLoginMemberDetails() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ServiceMemberGetResponseDto.builder()
                .email( ((PrincipalDetails) authentication.getPrincipal()).getMember().getEmail())
                .nickname( ((PrincipalDetails) authentication.getPrincipal()).getMember().getNickname())
                .role( ((PrincipalDetails) authentication.getPrincipal()).getMember().getRole())
                .wallet( ((PrincipalDetails) authentication.getPrincipal()).getMember().getWallet())
                .profileMessage( ((PrincipalDetails) authentication.getPrincipal()).getMember().getProfileMessage())
                .notice( ((PrincipalDetails) authentication.getPrincipal()).getMember().getNotice())
                .memberSeq(((PrincipalDetails) authentication.getPrincipal()).getMember().getSeq())
                .build();
    }

    public Object downloadProfileImage(long memberSeq) {
        Member findMember = memberRepository.findById(memberSeq).orElseThrow(IllegalArgumentException::new);
        return FileUtils.getUrlResource(findMember.getImageUuid());
    }

    public Object downloadBackgroundImage(long memberSeq) {
        Member findMember = memberRepository.findById(memberSeq).orElseThrow(IllegalArgumentException::new);
        return FileUtils.getUrlResource(findMember.getBackgroundUuid());
    }
}
