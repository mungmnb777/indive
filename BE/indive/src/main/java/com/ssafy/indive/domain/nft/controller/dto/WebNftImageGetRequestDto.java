package com.ssafy.indive.domain.nft.controller.dto;

import com.ssafy.indive.domain.nft.service.dto.ServiceNftImageGetRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebNftImageGetRequestDto {
    private String cid;

    public WebNftImageGetRequestDto(String cid) {
        this.cid = cid;
    }

    public ServiceNftImageGetRequestDto convertToService() {
        return new ServiceNftImageGetRequestDto(cid);
    }
}
