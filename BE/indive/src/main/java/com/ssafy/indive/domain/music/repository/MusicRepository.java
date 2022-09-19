package com.ssafy.indive.domain.music.repository;

import com.ssafy.indive.domain.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
