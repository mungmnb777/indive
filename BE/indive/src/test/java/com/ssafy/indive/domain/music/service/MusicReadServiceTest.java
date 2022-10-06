package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.MockEntityFactory;
import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.music.controller.dto.WebMusicGetCondition;
import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.entity.Reply;
import com.ssafy.indive.domain.music.repository.MusicLikeRepository;
import com.ssafy.indive.domain.music.repository.MusicQueryRepository;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicGetResponseDto;
import com.ssafy.indive.domain.music.service.dto.ServiceReplyGetResponseDto;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("음원 조회 서비스 단위 테스트")
public class MusicReadServiceTest {

    @InjectMocks
    private MusicReadService musicReadService;

    @Mock
    private MusicQueryRepository musicQueryRepository;

    @Mock
    private MusicRepository musicRepository;

    @Mock
    private MusicLikeRepository musicLikeRepository;

    @Nested
    @DisplayName("[음원 리스트 조회] 사용자는 음원을 조회할 수 있어야 한다.")
    class GetMusic {

        @Test
        @DisplayName("[성공 케이스]")
        public void success1() {
            // given
            WebMusicGetCondition condition = WebMusicGetCondition.builder().build();

            Pageable pageable = PageRequest.of(0, 20);

            List<Music> music = new ArrayList<>();

            music.add(MockEntityFactory.music());

            given(musicQueryRepository.findAll(condition, pageable)).willReturn(music);

            // when
            List<ServiceMusicGetResponseDto> dtos = musicReadService.getMusic(condition, pageable);

            // then
            ServiceMusicGetResponseDto dto = dtos.get(0);

            assertThat(dto.getTitle()).isEqualTo("제목");
            assertThat(dto.getLyricist()).isEqualTo("작사가");
            assertThat(dto.getComposer()).isEqualTo("작곡가");
            assertThat(dto.getGenre()).isEqualTo("장르");
            assertThat(dto.getDescription()).isEqualTo("설명");
            assertThat(dto.getLyrics()).isEqualTo("가사");
            assertThat(dto.getReleaseDateTime()).isEqualTo("2022-09-20 12:00:00");
            assertThat(dto.getReservationDateTime()).isEqualTo("2022-09-20 12:10:00");

            verify(musicQueryRepository, times(1)).findAll(any(WebMusicGetCondition.class), any(Pageable.class));
        }
    }

    @Nested
    @DisplayName("[내 음원 조회] 사용자는 음원을 조회할 수 있어야 한다.")
    class GetMyMusic {

        @Test
        @DisplayName("[성공 케이스]")
        public void success1() {
            // given
            WithMockSecurityContextFactory.createSecurityContext();

            Pageable pageable = PageRequest.of(0, 20);

            List<Music> music = new ArrayList<>();

            music.add(MockEntityFactory.music());

            given(musicQueryRepository.findAllByAuthor(any(Member.class), any(Pageable.class))).willReturn(music);

            // when
            List<ServiceMusicGetResponseDto> dtos = musicReadService.getMyMusic(pageable);

            // then
            ServiceMusicGetResponseDto dto = dtos.get(0);

            assertThat(dto.getTitle()).isEqualTo("제목");
            assertThat(dto.getLyricist()).isEqualTo("작사가");
            assertThat(dto.getComposer()).isEqualTo("작곡가");
            assertThat(dto.getGenre()).isEqualTo("장르");
            assertThat(dto.getDescription()).isEqualTo("설명");
            assertThat(dto.getLyrics()).isEqualTo("가사");
            assertThat(dto.getReleaseDateTime()).isEqualTo("2022-09-20 12:00:00");
            assertThat(dto.getReservationDateTime()).isEqualTo("2022-09-20 12:10:00");

            verify(musicQueryRepository, times(1)).findAllByAuthor(any(Member.class), any(Pageable.class));
        }
    }

    @Nested
    @DisplayName("[음원 세부 사항 조회] 사용자는 음원의 세부사항을 조회할 수 있어야 한다.")
    class getMusicDetails {

        private Long seq;

        @BeforeEach
        void beforeEach() {
            seq = 1L;
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
            ServiceMusicGetResponseDto musicDetails = musicReadService.getMusicDetails(seq);

            // then
            assertThat(musicDetails.getTitle()).isEqualTo("제목");
            assertThat(musicDetails.getLyricist()).isEqualTo("작사가");
            assertThat(musicDetails.getComposer()).isEqualTo("작곡가");
            assertThat(musicDetails.getGenre()).isEqualTo("장르");
            assertThat(musicDetails.getDescription()).isEqualTo("설명");
            assertThat(musicDetails.getLyrics()).isEqualTo("가사");
            assertThat(musicDetails.getReleaseDateTime()).isEqualTo("2022-09-20 12:00:00");
            assertThat(musicDetails.getReservationDateTime()).isEqualTo("2022-09-20 12:10:00");

            verify(musicRepository, times(1)).findById(eq(seq));
        }
    }

    @Nested
    @DisplayName("[좋아요 확인] 좋아요를 눌렀는지 확인할 수 있다.")
    class IsLike{

        Long musicSeq;

        @BeforeEach
        void beforeEach() {
            musicSeq = 1L;

            WithMockSecurityContextFactory.createSecurityContext();
        }

        @Test
        @DisplayName("[성공 케이스 1] 좋아요를 누른 경우")
        public void success1() {
            // given
            given(musicRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.music()));

            given(musicLikeRepository.findByMusicAndLiker(any(Music.class), any(Member.class))).willReturn(Optional.of(MockEntityFactory.musicLike()));

            // when
            boolean isLike = musicReadService.isLike(musicSeq);

            // then
            assertThat(isLike).isTrue();

            verify(musicRepository, times(1)).findById(eq(1L));
            verify(musicLikeRepository, times(1)).findByMusicAndLiker(any(Music.class), any(Member.class));
        }

        @Test
        @DisplayName("[성공 케이스 2] 좋아요를 누르지 않은 경우")
        public void success2() {
            // given
            given(musicRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.music()));

            given(musicLikeRepository.findByMusicAndLiker(any(Music.class), any(Member.class))).willReturn(Optional.empty());

            // when
            boolean isLike = musicReadService.isLike(musicSeq);

            // then
            assertThat(isLike).isFalse();

            verify(musicRepository, times(1)).findById(eq(1L));
            verify(musicLikeRepository, times(1)).findByMusicAndLiker(any(Music.class), any(Member.class));
        }

        @Test
        @DisplayName("[실패 케이스] musicSeq가 잘못 입력된 경우")
        public void failure() {
            // given
            given(musicRepository.findById(eq(1L))).willReturn(Optional.empty());

            // when

            // then
            assertThatThrownBy(() -> musicReadService.isLike(musicSeq)).isInstanceOf(IllegalArgumentException.class);

            verify(musicRepository, times(1)).findById(eq(1L));
            verify(musicLikeRepository, times(0)).findByMusicAndLiker(any(Music.class), any(Member.class));
        }
    }

    @Nested
    @DisplayName("[좋아요 개수 조회] 사용자는 음원의 좋아요 개수를 확인할 수 있습니다.")
    class GetLikeCount {

        Long musicSeq;

        @BeforeEach
        void beforeEach() {
            musicSeq = 1L;
        }

        @Test
        @DisplayName("[성공 케이스]")
        public void success() {
            // given
            given(musicRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.music()));

            // when
            int likeCount = musicReadService.getLikeCount(musicSeq);

            // then
            assertThat(likeCount).isEqualTo(0);

            verify(musicRepository, times(1)).findById(eq(1L));
        }

        @Test
        @DisplayName("[실패 케이스] 음원이 없는 경우")
        public void failure() {
            // given
            given(musicRepository.findById(eq(1L))).willReturn(Optional.empty());

            // when

            // then
            assertThatThrownBy(() -> musicReadService.getLikeCount(musicSeq)).isInstanceOf(IllegalArgumentException.class);


            verify(musicRepository, times(1)).findById(eq(1L));
        }
    }

    @Nested
    @DisplayName("[댓글 조회] 사용자는 댓글을 조회할 수 있다.")
    class GetMusicReply {

        long musicSeq;

        @BeforeEach
        void beforeEach() {
            musicSeq = 1L;
        }

        @Test
        @DisplayName("[성공 케이스]")
        public void success() {
            // given
            given(musicRepository.findById(eq(1L))).will(
                    (Answer<Optional<Music>>) invocation -> {
                        Music music = MockEntityFactory.music();

                        Reply reply = Reply.builder()
                                .music(music)
                                .author(MockEntityFactory.member())
                                .content("댓글")
                                .build();

                        ReflectionTestUtils.setField(music, "seq", 1L);
                        ReflectionTestUtils.setField(reply, "seq", 1L);

                        return Optional.of(music);
                    }
            );

            // when
            List<ServiceReplyGetResponseDto> musicReply = musicReadService.getMusicReply(musicSeq);

            // then
            ServiceReplyGetResponseDto dto = musicReply.get(0);

            assertThat(dto.getContent()).isEqualTo("댓글");

            verify(musicRepository, times(1)).findById(eq(1L));
        }

        @Test
        @DisplayName("[실패 케이스] 음원이 없는 경우")
        public void failure() {
            // given
            given(musicRepository.findById(eq(1L))).willReturn(Optional.empty());

            // when

            // then
            assertThatThrownBy(() -> musicReadService.getMusicReply(musicSeq)).isInstanceOf(IllegalArgumentException.class);

            verify(musicRepository, times(1)).findById(eq(1L));
        }
    }
}
