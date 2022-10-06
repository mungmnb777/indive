package com.ssafy.indive.domain.nft.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.nft.entity.Nft;
import com.ssafy.indive.domain.nft.entity.QNft;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.ssafy.indive.domain.music.entity.QMusic.music;
import static com.ssafy.indive.domain.nft.entity.QNft.*;
import static com.ssafy.indive.domain.nft.entity.QNft.nft;

@Repository
public class NftQueryRepository {

    private final JPAQueryFactory query;

    public NftQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public Optional<Nft> findByArtist(Member artist) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(nft.artist.eq(artist));
        builder.and(nft.stock.gt(0));

        Nft findNft = query.select(nft)
                .from(nft)
                .where(builder)
                .orderBy(new OrderSpecifier<>(Order.ASC, nft.createDate))
                .fetchFirst();

        return Optional.ofNullable(findNft);
    }
}
