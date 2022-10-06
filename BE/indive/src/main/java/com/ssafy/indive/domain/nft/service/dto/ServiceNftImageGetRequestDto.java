package com.ssafy.indive.domain.nft.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceNftImageGetRequestDto {

    private String cid;

    public ServiceNftImageGetRequestDto(String cid) {
        this.cid = cid;
    }
}
