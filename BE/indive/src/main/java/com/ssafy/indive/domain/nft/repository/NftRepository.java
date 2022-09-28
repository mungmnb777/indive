package com.ssafy.indive.domain.nft.repository;

import com.ssafy.indive.domain.nft.entity.Nft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NftRepository extends JpaRepository<Nft, Long> {
}
