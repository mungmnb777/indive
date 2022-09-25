package com.ssafy.indive.domain.reward.repository;

import com.ssafy.indive.domain.reward.entity.RewardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RewardItemRepository extends JpaRepository<RewardItem, Long> {
    @Query("select ri from RewardItem ri join fetch ri.artist where ri.artist.seq = :artistSeq")
    List<RewardItem> findByArtistSeq(@Param("artistSeq") Long artistSeq);
}
