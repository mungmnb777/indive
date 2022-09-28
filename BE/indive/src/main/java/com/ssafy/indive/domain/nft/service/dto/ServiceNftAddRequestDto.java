package com.ssafy.indive.domain.nft.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceNftAddRequestDto {


    private MultipartFile image;

    private int lowerDonationAmount;

    private int stock;

    public ServiceNftAddRequestDto(MultipartFile image, int lowerDonationAmount, int stock) {
        this.image = image;
        this.lowerDonationAmount = lowerDonationAmount;
        this.stock = stock;
    }
}
