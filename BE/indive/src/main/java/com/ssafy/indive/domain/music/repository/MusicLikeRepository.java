package com.ssafy.indive.domain.music.repository;

import com.ssafy.indive.domain.music.entity.MusicLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicLikeRepository extends JpaRepository<MusicLike, Long> {
}
