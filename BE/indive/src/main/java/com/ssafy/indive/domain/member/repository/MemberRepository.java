package com.ssafy.indive.domain.member.repository;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByNickname(String username);
}
