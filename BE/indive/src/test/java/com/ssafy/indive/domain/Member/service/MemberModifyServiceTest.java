package com.ssafy.indive.domain.Member.service;


import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.domain.member.service.MemberModifyService;
import com.ssafy.indive.domain.member.service.MemberReadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("멤버 수정 서비스 단위 테스트")
public class MemberModifyServiceTest {

    @InjectMocks
    private MemberModifyService memberModifyService;

    @Mock
    private MemberRepository memberRepository;


    @Nested
    @DisplayName("[멤버 정보 수정] 사용자의 정보를 수정할 수 있어야 한다.")
    class modifyMember{


    }

    @Nested
    @DisplayName("[공지사항 수정] 공지사항을 수정할 수 있어야 한다.")
    class writeNotice{

    }
}
