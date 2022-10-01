package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.MockEntityFactory;
import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicModifyRequestDto;
import com.ssafy.indive.utils.security.factory.WithMockSecurityContextFactory;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("멤버 수정 서비스 단위 테스트")
public class MusicModifyServiceTest {

    @InjectMocks
    private MusicModifyService musicModifyService;

    @Mock
    private MusicRepository musicRepository;

    @Nested
    @DisplayName("[음원 수정] 사용자는 음원을 수정할 수 있어야 한다.")
    class ModifyMusic {

        private Long seq;
        private MockMultipartFile image;
        private MockMultipartFile musicFile;

        @BeforeEach
        void beforeEach() throws URISyntaxException, IOException {
            seq = 1L;

            image = new MockMultipartFile("file", "image.png", "image/png", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("image.png").toURI())));

            musicFile = new MockMultipartFile("file", "musicFile.mp3", "audio/mpeg", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("musicFile.mp3").toURI())));

            WithMockSecurityContextFactory.createSecurityContext();
        }

        @AfterEach
        void afterEach() throws IOException {
            FileUtils.deleteDirectory(new File("null"));
        }

        @Test
        @DisplayName("성공 케이스")
        public void successCase() {
            // given
            given(musicRepository.findById(eq(seq))).will(
                    (Answer<Optional<Music>>) invocation -> {
                        Music expected = MockEntityFactory.music();

                        ReflectionTestUtils.setField(expected, "seq", seq);

                        return Optional.of(expected);
                    }
            );

            ServiceMusicModifyRequestDto dto = getTestDto();

            // when
            boolean isSuccess = musicModifyService.modifyMusic(seq, dto);

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

        private ServiceMusicModifyRequestDto getTestDto() {
            return ServiceMusicModifyRequestDto.builder()
                    .title("제목")
                    .lyricist("작사가")
                    .composer("작곡가")
                    .genre("장르")
                    .description("설명")
                    .lyrics("가사")
                    .releaseDateTime(LocalDateTime.of(2022, 9, 20, 12, 0))
                    .reservationDateTime(LocalDateTime.of(2022, 9, 20, 12, 0).plusMinutes(10L))
                    .image(image)
                    .musicFile(musicFile)
                    .build();
        }
    }
}
