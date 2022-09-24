package com.ssafy.indive.domain.reward.repository;

import com.ssafy.indive.domain.reward.entity.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {
}
