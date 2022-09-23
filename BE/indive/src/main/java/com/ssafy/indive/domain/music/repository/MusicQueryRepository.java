package com.ssafy.indive.domain.music.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.indive.domain.music.controller.dto.WebMusicGetCondition;
import com.ssafy.indive.domain.music.entity.Music;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ssafy.indive.domain.music.entity.QMusic.music;

@Repository
public class MusicQueryRepository {

    private final JPAQueryFactory query;

    public MusicQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Music> findAll(WebMusicGetCondition condition) {
        String title = condition.getTitle();
        String artist = condition.getArtist();
        String genre = condition.getGenre();
        String sort = condition.getSort();

        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(title)) builder.and(music.title.like("%" + title + "%"));
        if (StringUtils.hasText(artist)) builder.and(music.author.nickname.like("%" + artist + "%"));
        if (StringUtils.hasText(genre)) builder.and(music.genre.like("%" + genre + "%"));

        return query.select(music)
                .from(music)
                .where(builder)
                .orderBy(sort(sort))
                .fetch();
    }

    private OrderSpecifier<?> sort(String sort) {
        if (!StringUtils.hasText(sort)) return new OrderSpecifier<>(Order.ASC, music.title);

        switch (sort) {
            case "latest":
                return new OrderSpecifier<>(Order.DESC, music.createDate);
            case "popular":
                return new OrderSpecifier<>(Order.DESC, music.likeCount);
            default:
                return null;
        }
    }
}
