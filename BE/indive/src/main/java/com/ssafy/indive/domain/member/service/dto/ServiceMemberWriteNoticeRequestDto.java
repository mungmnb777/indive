package com.ssafy.indive.domain.member.service.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceMemberWriteNoticeRequestDto {

    private String notice;

    @Builder
    public ServiceMemberWriteNoticeRequestDto(String notice) {
        this.notice = notice;
    }
}
