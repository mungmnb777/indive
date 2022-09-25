package com.ssafy.indive.domain.reward.service;

import com.ssafy.indive.domain.reward.entity.RewardItem;
import com.ssafy.indive.domain.reward.repository.RewardItemRepository;
import com.ssafy.indive.domain.reward.service.dto.ServiceItemModifyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardModifyService {

    private final RewardItemRepository rewardItemRepository;

    public boolean modifyItem(long riSeq, ServiceItemModifyRequestDto dto) {
        RewardItem item = rewardItemRepository.findById(riSeq).orElseThrow(IllegalArgumentException::new);

        item.update(dto);

        return true;
    }
}
