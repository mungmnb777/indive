package com.ssafy.indive.domain.Member.service;


import com.ssafy.indive.domain.MockEntityFactory;
import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.domain.member.service.MemberModifyService;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberModifyRequestDto;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberWriteNoticeRequestDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

        MockMultipartFile image;
        MockMultipartFile background;
        long memberSeq = 1L;

        @BeforeEach
        void beforeEach() throws URISyntaxException, IOException {
            image = new MockMultipartFile("file", "image.png", "image/png", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("image.png").toURI())));
            background = new MockMultipartFile("file", "background.png", "image/png", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("background.png").toURI())));
        }

        @Test
        @DisplayName("[성공 케이스 1] 프로필, 배경 사진 둘 다 있는 경우")
        public void success1() {
            // given
            ServiceMemberModifyRequestDto dto = getServiceDto(image, background);

            given(memberRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.member()));

            // when
            boolean isSuccess = memberModifyService.modifyMember(memberSeq, dto);

            // then
            assertThat(isSuccess).isTrue();

            verify(memberRepository, times(1)).findById(eq(1L));
            verify(memberRepository, times(1)).save(any(Member.class));
        }

        @Test
        @DisplayName("[성공 케이스 2] 프로필, 배경 사진 둘 다 없는 경우")
        public void success2() {
            // given
            ServiceMemberModifyRequestDto dto = getServiceDto(null, null);

            given(memberRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.member()));

            // when
            boolean isSuccess = memberModifyService.modifyMember(memberSeq, dto);

            // then
            assertThat(isSuccess).isTrue();

            verify(memberRepository, times(1)).findById(eq(1L));
            verify(memberRepository, times(1)).save(any(Member.class));
        }

        @Test
        @DisplayName("[성공 케이스 3] 프로필 사진만 없는 경우")
        public void success3() {
            // given
            ServiceMemberModifyRequestDto dto = getServiceDto(null, background);

            given(memberRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.member()));

            // when
            boolean isSuccess = memberModifyService.modifyMember(memberSeq, dto);

            // then
            assertThat(isSuccess).isTrue();

            verify(memberRepository, times(1)).findById(eq(1L));
            verify(memberRepository, times(1)).save(any(Member.class));
        }

        @Test
        @DisplayName("[성공 케이스 4] 배경 사진만 없는 경우")
        public void success4() {
            // given
            ServiceMemberModifyRequestDto dto = getServiceDto(image, null);

            given(memberRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.member()));

            // when
            boolean isSuccess = memberModifyService.modifyMember(memberSeq, dto);

            // then
            assertThat(isSuccess).isTrue();

            verify(memberRepository, times(1)).findById(eq(1L));
            verify(memberRepository, times(1)).save(any(Member.class));
        }

        @Test
        @DisplayName("[실패 케이스] 해당 SEQ의 멤버가 없는 경우")
        public void failure() {
            // given
            ServiceMemberModifyRequestDto dto = getServiceDto(image, background);

            given(memberRepository.findById(eq(1L))).willReturn(Optional.empty());

            // when

            // then
            assertThatThrownBy(() -> memberModifyService.modifyMember(memberSeq, dto)).isInstanceOf(IllegalArgumentException.class);

            verify(memberRepository, times(1)).findById(eq(1L));
            verify(memberRepository, times(0)).save(any(Member.class));
        }

        private ServiceMemberModifyRequestDto getServiceDto(MockMultipartFile image, MockMultipartFile background) {
            return ServiceMemberModifyRequestDto.builder()
                    .image(image)
                    .background(background)
                    .nickname("명범")
                    .profileMessage("프로필")
                    .build();
        }
    }

    @Nested
    @DisplayName("[공지사항 수정] 공지사항을 수정할 수 있어야 한다.")
    class writeNotice{
        long memberSeq;
        ServiceMemberWriteNoticeRequestDto dto;

        @BeforeEach
        void beforeEach() {
            memberSeq = 1L;
            dto = ServiceMemberWriteNoticeRequestDto.builder()
                    .notice("수정 공지사항")
                    .build();
        }

        @Test
        @DisplayName("[성공 케이스]")
        public void success() {
            // given
            given(memberRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.member()));

            // when
            boolean isSuccess = memberModifyService.writeNotice(dto, memberSeq);

            // then
            assertThat(isSuccess).isTrue();

            verify(memberRepository, times(1)).findById(eq(1L));
        }

        @Test
        @DisplayName("[실패 케이스] 해당 SEQ의 멤버가 없는 경우")
        public void failure() {
            // given
            given(memberRepository.findById(eq(1L))).willReturn(Optional.empty());

            // when

            // then
            assertThatThrownBy(() -> memberModifyService.writeNotice(dto, memberSeq)).isInstanceOf(IllegalArgumentException.class);

            verify(memberRepository, times(1)).findById(eq(1L));
        }
    }
}
