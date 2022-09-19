package com.ssafy.indive.domain.member.repository;

import com.ssafy.indive.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
