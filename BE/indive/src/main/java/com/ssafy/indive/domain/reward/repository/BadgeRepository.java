package com.ssafy.indive.domain.reward.repository;

import com.ssafy.indive.domain.reward.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
