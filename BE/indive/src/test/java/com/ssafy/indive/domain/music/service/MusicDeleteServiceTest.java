package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.repository.MusicRepository;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("멤버 삭제 서비스 단위 테스트")
public class MusicDeleteServiceTest {

    @InjectMocks
    private MusicDeleteService musicDeleteService;

    @Mock
    private MusicRepository musicRepository;

    @Nested
    @DisplayName("[음원 삭제] 사용자는 음원을 삭제할 수 있어야 한다.")
    class DeleteMusic {

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
            boolean isSuccess = musicDeleteService.deleteMusic(seq);

            // then
            assertThat(isSuccess).isTrue();

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
