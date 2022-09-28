package com.ssafy.indive.domain.music.repository;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.entity.MusicLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MusicLikeRepository extends JpaRepository<MusicLike, Long> {

    Optional<MusicLike> findByMusicAndLiker(Music music, Member liker);

    void deleteByMusicAndLiker(Music music, Member liker);
}
