package com.ssafy.indive.domain.nft.controller;

import com.ssafy.indive.domain.nft.controller.dto.WebCheckAmountGetRequestDto;
import com.ssafy.indive.domain.nft.controller.dto.WebCheckStockGetRequestDto;
import com.ssafy.indive.domain.nft.controller.dto.WebNftAddRequestDto;
import com.ssafy.indive.domain.nft.controller.dto.WebNftModifyRequestDto;
import com.ssafy.indive.domain.nft.exception.NftNotFoundException;
import com.ssafy.indive.domain.nft.exception.NotSatisfiedAmountException;
import com.ssafy.indive.domain.nft.service.NftAddService;
import com.ssafy.indive.domain.nft.service.NftModifyService;
import com.ssafy.indive.domain.nft.service.NftReadService;
import com.ssafy.indive.domain.nft.service.facade.NftModifyServiceRedisFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nft")
public class NftController {

    private final NftAddService nftAddService;

    private final NftModifyServiceRedisFacade nftModifyService;

    private final NftReadService nftReadService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addImageToIpfs(@Validated @ModelAttribute WebNftAddRequestDto dto) throws IOException {
        return new ResponseEntity<>(nftAddService.addImageToIpfs(dto.convertToServiceDto()), HttpStatus.OK);
    }

    @GetMapping("/check-stock")
    public ResponseEntity<?> checkStock(@Validated @ModelAttribute WebCheckStockGetRequestDto dto) {
        try {
            return new ResponseEntity<>(nftReadService.checkStock(dto.convertToService()), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        } catch (NftNotFoundException e) {
            return new ResponseEntity<>(0, HttpStatus.OK);
        }
    }

    @GetMapping("/check-amount")
    public ResponseEntity<?> checkAmount(@Validated @ModelAttribute WebCheckAmountGetRequestDto dto) {
        try {
            return new ResponseEntity<>(nftReadService.checkAmount(dto.convertToService()), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        } catch (NftNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> issueNft(@RequestBody WebNftModifyRequestDto dto) {
        try {
            return new ResponseEntity<>(nftModifyService.issueNft(dto.convertToService()), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        } catch (NftNotFoundException | NotSatisfiedAmountException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
