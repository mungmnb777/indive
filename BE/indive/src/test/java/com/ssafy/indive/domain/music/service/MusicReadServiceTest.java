package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.repository.MusicQueryRepository;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicGetResponseDto;
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
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("멤버 조회 서비스 단위 테스트")
public class MusicReadServiceTest {

    @InjectMocks
    private MusicReadService musicReadService;

    @Mock
    private MusicQueryRepository musicQueryRepository;

    @Mock
    private MusicRepository musicRepository;

    @Nested
    @DisplayName("[음원 세부 사항 조회] 사용자는 음원의 세부사항을 조회할 수 있어야 한다.")
    class getMusicDetails {

        private Long seq;

        @BeforeEach
        void beforeEach() {
            seq = 1L;
        }

        @Test
        @DisplayName("성공 케이스")
        public void successCase() {
            // given
            given(musicRepository.findById(eq(seq))).will(
                    (Answer<Optional<Music>>) invocation -> {
                        Music expected = getTestEntity();

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

        private Music getTestEntity() {
            return Music.builder()
                    .title("제목")
                    .lyricist("작사가")
                    .composer("작곡가")
                    .genre("장르")
                    .description("설명")
                    .lyrics("가사")
                    .releaseDatetime(LocalDateTime.of(2022, 9, 20, 12, 0))
                    .reservationDatetime(LocalDateTime.of(2022, 9, 20, 12, 0).plusMinutes(10L))
                    .build();
        }
    }

}
