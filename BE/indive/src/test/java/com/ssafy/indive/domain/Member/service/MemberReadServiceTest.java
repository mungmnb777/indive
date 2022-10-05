package com.ssafy.indive.domain.Member.service;

import com.ssafy.indive.domain.MockEntityFactory;
import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.entity.enumeration.Role;
import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.domain.member.service.MemberReadService;
import com.ssafy.indive.domain.member.service.dto.ServiceDuplicatedEmail;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberGetResponseDto;
import com.ssafy.indive.utils.security.factory.WithMockSecurityContextFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("멤버 조회 서비스 단위 테스트")
public class MemberReadServiceTest {

    @InjectMocks
    private MemberReadService memberReadService;

    @Mock
    private MemberRepository memberRepository;


    @Nested
    @DisplayName("[이메일 중복체크] 이메일 값이 중복인지 체크할 수 있다.")
    class isDuplicated{

        @Test
        @DisplayName("[성공 케이스1] 이메일이 중복이다.")
        public void success1(){
            //given
            ServiceDuplicatedEmail condition = ServiceDuplicatedEmail.builder().build();

            given(memberRepository.existsByEmail(condition.convertToString())).willReturn(true);

            //when
            boolean isSuccess = memberReadService.isDuplicated(condition);

            //then
            assertThat(isSuccess).isTrue();
            verify(memberRepository,times(1)).existsByEmail(condition.convertToString());
        }

        @Test
        @DisplayName("[성공 케이스2] 이메일이 중복이 아니다.")
        public void success2(){
            //중복되는 걸 만드는 게 아니라 그냥 true 만 나오게 하면 된다.
            //가짜 객체를 넣었다는 것 -> repo로 안 넘어가는 것
            //내부 동작을 이해할 필요가 없음. 그냥 메소드가 만드는 return 만 생각하라

            //given
            ServiceDuplicatedEmail condition = ServiceDuplicatedEmail.builder().build();

            given(memberRepository.existsByEmail(condition.convertToString())).willReturn(false);

            //when
            boolean isSuccess = memberReadService.isDuplicated(condition);

            //then
            assertThat(isSuccess).isFalse();
            verify(memberRepository,times(1)).existsByEmail(condition.convertToString());

        }
    }

    @Nested
    @DisplayName("[멤버 세부정보 조회] 멤버의 세부정보를 조회할 수 있어야 한다.")
    class getMemberDetails{

        private Long seq;

        @BeforeEach
        void beforeEach(){
            seq = 1L;
        }

        @Test
        @DisplayName("[성공 케이스]")
        public void success1(){
            //플랫하게!
            //given (형태 체크) 여기서 가짜를 만들고
            given(memberRepository.findBySeq(eq(seq))).will(
                    (Answer< Optional<Member>>) invocation -> {
                        Member expected = MockEntityFactory.member();

                        ReflectionTestUtils.setField(expected,"seq",seq);

                        return Optional.of(expected);
                    }
            );

            //when (실제로 실행시킴) 여기서 가짜를 가져오는 것
            ServiceMemberGetResponseDto memberdetail = memberReadService.getMemberDetails(seq);

            //then 리턴값이 똑같이 나왔는가
            assertThat(memberdetail.getEmail()).isEqualTo("mungmnb777@gmail.com");
            assertThat(memberdetail.getNickname()).isEqualTo("명범짱");
            assertThat(memberdetail.getNotice()).isEqualTo("공지사항");
            assertThat(memberdetail.getProfileMessage()).isEqualTo("프로필 상태 메시지");
            assertThat(memberdetail.getWallet()).isEqualTo("지갑");
            assertThat(memberdetail.getRole()).isEqualTo(Role.ROLE_USER);

                //몇 번 실행되는가
            verify(memberRepository,times(1)).findBySeq(eq(1L));

        }

        @Test
        @DisplayName("[실패 케이스] memberSeq가 잘못된 경우")
        public void failure(){
            //given
            given(memberRepository.findBySeq(eq(1L))).willReturn(Optional.empty());

            //then
            assertThatThrownBy(()-> memberReadService.getMemberDetails(1L)).isInstanceOf(IllegalArgumentException.class);
            verify(memberRepository,times(1)).findBySeq(eq(1L));
        }

    }

    @Nested
    @DisplayName("[로그인한 멤버정보 조회] 로그인한 멤버의 세부정보를 조회할 수 있어야 한다.")
    class getLoginMemberDetails{


        @BeforeEach
        void beforeEach() throws IOException{
            WithMockSecurityContextFactory.createSecurityContext();
        }

        @Test
        @DisplayName("[성공 케이스]")
        public void success() {
            //then
            ServiceMemberGetResponseDto memberdetail = memberReadService.getLoginMemberDetails();

            assertThat(memberdetail.getEmail()).isEqualTo("mungmnb777@gmail.com");
            assertThat(memberdetail.getNickname()).isEqualTo("명범짱");
            assertThat(memberdetail.getNotice()).isEqualTo("공지사항");
            assertThat(memberdetail.getProfileMessage()).isEqualTo("프로필 상태 메시지");
            assertThat(memberdetail.getWallet()).isEqualTo("지갑");
            assertThat(memberdetail.getRole()).isEqualTo(Role.ROLE_USER);
        }


    }

}
