package com.ssafy.indive.domain.nft.controller.dto;

import com.ssafy.indive.domain.nft.service.dto.ServiceNftAddRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WebNftAddRequestDto {

    private MultipartFile image;

    private int lowerDonationAmount;

    private int stock;

    public WebNftAddRequestDto(MultipartFile image, int lowerDonationAmount, int stock) {
        this.image = image;
        this.lowerDonationAmount = lowerDonationAmount;
        this.stock = stock;
    }

    public ServiceNftAddRequestDto convertToServiceDto() {
        return new ServiceNftAddRequestDto(image, lowerDonationAmount, stock);
    }
}
