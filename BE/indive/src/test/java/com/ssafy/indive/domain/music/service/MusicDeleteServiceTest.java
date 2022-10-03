package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.MockEntityFactory;
import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.exception.NotMatchMemberException;
import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.entity.MusicLike;
import com.ssafy.indive.domain.music.repository.MusicLikeRepository;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.repository.ReplyRepository;
import com.ssafy.indive.security.config.auth.PrincipalDetails;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("음원 삭제 서비스 단위 테스트")
public class MusicDeleteServiceTest {

    @InjectMocks
    private MusicDeleteService musicDeleteService;

    @Mock
    private MusicRepository musicRepository;

    @Mock
    private MusicLikeRepository musicLikeRepository;

    @Mock
    private ReplyRepository replyRepository;

    @Nested
    @DisplayName("[음원 삭제] 사용자는 음원을 삭제할 수 있어야 한다.")
    class DeleteMusic {

        private Long seq;

        @BeforeEach
        void beforeEach() {
            seq = 1L;

            WithMockSecurityContextFactory.createSecurityContext();
        }

        @Test
        @DisplayName("[성공 케이스]")
        public void successCase() {
            // given
            given(musicRepository.findById(eq(seq))).will(
                    (Answer<Optional<Music>>) invocation -> {
                        Music expected = MockEntityFactory.music();

                        ReflectionTestUtils.setField(expected, "seq", seq);

                        return Optional.of(expected);
                    }
            );

            // when
            boolean isSuccess = musicDeleteService.deleteMusic(seq);

            // then
            assertThat(isSuccess).isTrue();

            verify(musicRepository, times(1)).findById(eq(seq));
        }

        @Test
        @DisplayName("[실패 케이스 1] 해당 음원이 없는 경우")
        public void failure1() {
            // given
            given(musicRepository.findById(eq(1L))).willReturn(Optional.empty());

            // when

            // then
            assertThatThrownBy(() -> musicDeleteService.deleteMusic(seq)).isInstanceOf(IllegalArgumentException.class);

            verify(musicRepository, times(1)).findById(eq(1L));
        }

        @Test
        @DisplayName("[실패 케이스 2] 로그인한 유저가 댓글의 주인이 아닌 경우")
        public void failure2() {
            // given
            SecurityContext context = SecurityContextHolder.getContext();

            PrincipalDetails principal = new PrincipalDetails(Member.builder().seq(2L).build());

            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

            context.setAuthentication(authentication);

            given(musicRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.music()));

            // when

            // then
            assertThatThrownBy(() -> musicDeleteService.deleteMusic(seq)).isInstanceOf(NotMatchMemberException.class).hasMessage("해당 음원의 주인이 아닙니다.");

            verify(musicRepository, times(1)).findById(eq(1L));
        }
    }

    @Nested
    @DisplayName("[좋아요 삭제] 사용자는 좋아요를 취소할 수 있다.")
    class DeleteMusicLike {

        Long musicSeq;

        @BeforeEach
        void beforeEach() {
            musicSeq = 1L;

            WithMockSecurityContextFactory.createSecurityContext();
        }

        @Test
        @DisplayName("[성공 케이스]")
        public void success1() {
            // given
            given(musicRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.music()));

            given(musicLikeRepository.findByMusicAndLiker(any(Music.class), any(Member.class))).willReturn(Optional.of(MockEntityFactory.musicLike()));

            // when
            boolean isSuccess = musicDeleteService.deleteLike(musicSeq);

            // then
            assertThat(isSuccess).isTrue();

            verify(musicRepository, times(1)).findById(eq(1L));
            verify(musicLikeRepository, times(1)).findByMusicAndLiker(any(Music.class), any(Member.class));
            verify(musicLikeRepository, times(1)).delete(any(MusicLike.class));
        }

        @Test
        @DisplayName("[실패 케이스 1] 음원이 없는 경우")
        public void failure1() {
            // given
            given(musicRepository.findById(eq(1L))).willReturn(Optional.empty());

            // when

            // then
            assertThatThrownBy(() -> musicDeleteService.deleteLike(musicSeq)).isInstanceOf(IllegalArgumentException.class);

            verify(musicRepository, times(1)).findById(eq(1L));
            verify(musicLikeRepository, times(0)).findByMusicAndLiker(any(Music.class), any(Member.class));
            verify(musicLikeRepository, times(0)).delete(any(MusicLike.class));
        }

        @Test
        @DisplayName("[실패 케이스 2] 이미 좋아요가 삭제된 경우")
        public void failure2() {
            // given
            given(musicRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.music()));

            given(musicLikeRepository.findByMusicAndLiker(any(Music.class), any(Member.class))).willReturn(Optional.empty());

            // when

            // then
            assertThatThrownBy(() -> musicDeleteService.deleteLike(musicSeq)).isInstanceOf(IllegalStateException.class);

            verify(musicRepository, times(1)).findById(eq(1L));
            verify(musicLikeRepository, times(1)).findByMusicAndLiker(any(Music.class), any(Member.class));
            verify(musicLikeRepository, times(0)).delete(any(MusicLike.class));
        }
    }

    @Nested
    @DisplayName("[댓글 삭제] 사용자는 댓글을 삭제할 수 있다.")
    class DeleteMusicReply {

        long replySeq;

        @BeforeEach
        void beforeEach() {
            replySeq = 1L;

            WithMockSecurityContextFactory.createSecurityContext();
        }

        @Test
        @DisplayName("[성공 케이스]")
        public void success1() {
            // given
            given(replyRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.reply()));

            // when
            boolean isSuccess = musicDeleteService.deleteMusicReply(replySeq);

            // then
            assertThat(isSuccess).isTrue();

            verify(replyRepository, times(1)).findById(eq(1L));
        }


        @Test
        @DisplayName("[실패 케이스 1] 해당 댓글이 없는 경우")
        public void failure1() {
            // given
            given(replyRepository.findById(eq(1L))).willReturn(Optional.empty());

            // when

            // then
            assertThatThrownBy(() -> musicDeleteService.deleteMusicReply(replySeq)).isInstanceOf(IllegalArgumentException.class);

            verify(replyRepository, times(1)).findById(eq(1L));
        }

        @Test
        @DisplayName("[실패 케이스 2] 로그인한 유저가 댓글의 주인이 아닌 경우")
        public void failure2() {
            // given
            SecurityContext context = SecurityContextHolder.getContext();

            PrincipalDetails principal = new PrincipalDetails(Member.builder().seq(2L).build());

            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

            context.setAuthentication(authentication);

            given(replyRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.reply()));

            // when

            // then
            assertThatThrownBy(() -> musicDeleteService.deleteMusicReply(replySeq)).isInstanceOf(NotMatchMemberException.class).hasMessage("해당 댓글의 주인이 아닙니다.");

            verify(replyRepository, times(1)).findById(eq(1L));
        }

    }
}
