package com.ssafy.indive.domain.member.service;

import com.ssafy.indive.domain.member.controller.dto.WebMemberModifyRequestDto;
import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberModifyRequestDto;
import com.ssafy.indive.global.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberModifyService {

    private final MemberRepository memberRepository;

    public boolean modifyMember(long seq, ServiceMemberModifyRequestDto dto) {


        Member findMember = memberRepository.findById(seq).orElseThrow(IllegalArgumentException::new);
        if(dto.getImage().isEmpty()){
            findMember.updateProfileImage();
        }
        if(dto.getBackground().isEmpty()) {
            findMember.updateBackgroundImage();
        }

        //파일 제외 필드 수정
        findMember.update(dto);

        System.out.println(dto.getProfileMessage());
        //이전 프로필사진, 배경사진 삭제
        FileUtils.deleteFile(findMember.getImageUuid());
        FileUtils.deleteFile(findMember.getBackgroundUuid());

        if(!dto.getImage().isEmpty() & !dto.getBackground().isEmpty()){
            findMember.uploadFiles(dto.getImage(), dto.getBackground());
        }

        memberRepository.save(findMember);

        return true;
    }


}
