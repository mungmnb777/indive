package com.ssafy.indive.domain.reward.controller;

import com.ssafy.indive.domain.reward.controller.dto.WebItemAddRequestDto;
import com.ssafy.indive.domain.reward.controller.dto.WebItemGetRequestDto;
import com.ssafy.indive.domain.reward.controller.dto.WebPointAddRequestDto;
import com.ssafy.indive.domain.reward.service.RewardAddService;
import com.ssafy.indive.domain.reward.service.RewardGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reward")
public class RewardController {

    private final RewardAddService rewardAddService;

    private final RewardGetService rewardGetService;

    @PostMapping("/points")
    public ResponseEntity<?> addPoint(@RequestBody WebPointAddRequestDto dto) {
        try {
            return new ResponseEntity<>(rewardAddService.addPoint(dto.convertToServiceDto()), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/items")
    public ResponseEntity<?> addItem(@RequestBody WebItemAddRequestDto dto) {
        return new ResponseEntity<>(rewardAddService.addItem(dto.convertToServiceDto()), HttpStatus.OK);
    }

    @GetMapping("/items")
    public ResponseEntity<?> getItems(@RequestBody WebItemGetRequestDto dto) {
        return new ResponseEntity<>(rewardGetService.getItems(dto.convertToServiceDto()), HttpStatus.OK);
    }

    @GetMapping("/items/{riSeq}")
    public ResponseEntity<?> getItemDetails(@PathVariable("riSeq") long riSeq) {
        try {
            return new ResponseEntity<>(rewardGetService.getItemDetails(riSeq), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("요청 값을 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
    }
}
