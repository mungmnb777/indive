package com.ssafy.indive.domain.member.service;

import com.ssafy.indive.domain.member.controller.dto.WebMemberModifyRequestDto;
import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberModifyRequestDto;
import com.ssafy.indive.global.utils.FileUtils;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberModifyService {

    private final MemberRepository memberRepository;

    public boolean modifyMember(long seq, ServiceMemberModifyRequestDto dto) {

        Member findMember = memberRepository.findById(seq).orElseThrow(IllegalArgumentException::new);

        findMember.update(dto);

        if((dto.getImage() != null && !dto.getImage().isEmpty()) && (dto.getBackground()!=null && !dto.getBackground().isEmpty())){
            log.info("modifyMember : 프로필, 배경사진 둘다있음");
            findMember.uploadFiles(dto.getImage(), dto.getBackground());
        }else if((dto.getImage() == null || dto.getImage().isEmpty()) && (dto.getBackground()==null || dto.getBackground().isEmpty())) {
            log.info("modifyMember : 프로필, 배경사진 둘다없음");
        }
        else if((dto.getImage() == null || dto.getImage().isEmpty()) && (dto.getBackground()!=null || !dto.getBackground().isEmpty())){
            log.info("modifyMember : 프로필 없음");
            //백그라운드 넣기
            FileUtils.deleteFile(findMember.getBackgroundUuid()); // 백그라운드 초기화
            findMember.uploadBackgroundFiles(dto.getBackground());
        }
        else if((dto.getImage() != null || !dto.getImage().isEmpty()) && (dto.getBackground()==null || dto.getBackground().isEmpty())) {
            log.info("modifyMember : 배경사진없음");
            //프로필 넣기
            FileUtils.deleteFile(findMember.getImageUuid());
            findMember.uploadProfileImage(dto.getImage());
        }

        memberRepository.save(findMember);

        return true;
    }


}
