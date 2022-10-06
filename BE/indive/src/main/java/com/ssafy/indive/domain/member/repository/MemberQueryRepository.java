package com.ssafy.indive.domain.member.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.entity.QMember;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class MemberQueryRepository {
    private final JPAQueryFactory query;

    public MemberQueryRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Member> findByWallet(List<String> wallets) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QMember.member.wallet.in(wallets));
        return query.select(QMember.member)
                .from(QMember.member)
                .where(builder)
                .fetch();
    }
}
