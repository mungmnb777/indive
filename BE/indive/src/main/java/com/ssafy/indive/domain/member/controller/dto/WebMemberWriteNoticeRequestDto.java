package com.ssafy.indive.domain.member.controller.dto;

import com.ssafy.indive.domain.member.service.dto.ServiceMemberModifyRequestDto;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberWriteNoticeRequestDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebMemberWriteNoticeRequestDto {

    private String notice;


    @Builder
    public ServiceMemberWriteNoticeRequestDto convertToServiceDto() {
        return ServiceMemberWriteNoticeRequestDto.builder()
            .notice(notice)
            .build();

    }
    @Builder
    public WebMemberWriteNoticeRequestDto(String notice) {
        this.notice = notice;
    }
}
