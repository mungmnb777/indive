package com.ssafy.indive.domain.reward.repository;

import com.ssafy.indive.domain.reward.entity.RewardItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardItemRepository extends JpaRepository<RewardItem, Long> {
}
