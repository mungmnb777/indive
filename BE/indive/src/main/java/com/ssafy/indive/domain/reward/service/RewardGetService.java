package com.ssafy.indive.domain.reward.service;

import com.ssafy.indive.domain.reward.entity.RewardItem;
import com.ssafy.indive.domain.reward.repository.RewardItemRepository;
import com.ssafy.indive.domain.reward.service.dto.ServiceItemGetRequestDto;
import com.ssafy.indive.domain.reward.service.dto.ServiceItemGetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardGetService {

    private final RewardItemRepository rewardItemRepository;

    // TODO: artist 엔티티를 DTO로 변경해야함
    public List<ServiceItemGetResponseDto> getItems(ServiceItemGetRequestDto requestDto) {
        List<RewardItem> items = rewardItemRepository.findByArtistSeq(requestDto.getArtistSeq());

        List<ServiceItemGetResponseDto> responseDtos = new ArrayList<>();

        for (RewardItem item : items) {
            ServiceItemGetResponseDto dto = ServiceItemGetResponseDto.builder()
                    .seq(item.getSeq())
                    .artist(item.getArtist())
                    .title(item.getTitle())
                    .content(item.getContent())
                    .point(item.getPoint())
                    .stock(item.getStock())
                    .build();

            responseDtos.add(dto);
        }

        return responseDtos;
    }

    // TODO: artist 엔티티를 DTO로 변경해야함
    public ServiceItemGetResponseDto getItemDetails(long riSeq) {
        RewardItem item = rewardItemRepository.findById(riSeq).orElseThrow(IllegalArgumentException::new);

        return ServiceItemGetResponseDto.builder()
                .seq(item.getSeq())
                .artist(item.getArtist())
                .title(item.getTitle())
                .content(item.getContent())
                .point(item.getPoint())
                .stock(item.getStock())
                .build();
    }
}
