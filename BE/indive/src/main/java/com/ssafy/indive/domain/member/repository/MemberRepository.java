package com.ssafy.indive.domain.member.repository;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.service.dto.ServiceDuplicatedEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickname(String username);

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String username);

    Optional<Member> findBySeq(long seq);
}
