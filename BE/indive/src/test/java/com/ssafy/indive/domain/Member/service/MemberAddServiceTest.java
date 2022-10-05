package com.ssafy.indive.domain.Member.service;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.domain.member.service.MemberAddService;
import com.ssafy.indive.domain.member.service.MemberReadService;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberAddRequestDto;
import com.ssafy.indive.utils.security.factory.WithMockSecurityContextFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
@DisplayName("멤버 get 서비스 단위 테스트")
public class MemberAddServiceTest {

    @InjectMocks
    private MemberAddService memberAddService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberReadService memberReadService;

    @Nested
    @DisplayName("[멤버 추가] 사용자는 회원 가입을 할 수 있어야 한다.")
    class addMember{

        @BeforeEach
        void beforeEach() throws IOException {
            WithMockSecurityContextFactory.createSecurityContext();
        }

        @Test
        @DisplayName("[성공 케이스]")
        public void successCase(){
            //given
             given(memberRepository.save(any(Member.class))).willReturn(Member.builder().build());

            ServiceMemberAddRequestDto dto = getTestDto();

            //when
            boolean isSuccess = memberAddService.addMember(dto);

            //then
            assertThat(isSuccess).isTrue();

            verify(memberRepository, times(1)).save(any(Member.class));
        }

        @Test
        @DisplayName("[실패 케이스] 중복 이메일인 경우 false 리턴") 
        public void failure1(){
            //TODO : 작성필
        }
        
        public ServiceMemberAddRequestDto getTestDto() {
            return ServiceMemberAddRequestDto.builder()
            .email("test@test")
            .password("test-password")
            .nickname("test-nickname")
            .wallet("test-wallet")
            .profileMessage("test-profile-message")
            .build();
        }
    }
}
