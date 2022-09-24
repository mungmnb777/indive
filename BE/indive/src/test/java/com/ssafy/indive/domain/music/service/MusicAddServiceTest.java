package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicAddRequestDto;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("멤버 서비스 단위 테스트")
class MusicAddServiceTest {

    @InjectMocks
    private MusicAddService musicAddService;

    @Mock
    private MusicRepository musicRepository;

    @Nested
    @DisplayName("[음원 등록] 사용자는 음원을 등록할 수 있어야 한다.")
    class AddMusic {
        MockMultipartFile image;
        MockMultipartFile musicFile;

        @BeforeEach
        void beforeEach() throws URISyntaxException, IOException {
            image = new MockMultipartFile("file", "image.png", "image/png", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("image.png").toURI())));
            musicFile = new MockMultipartFile("file", "musicFile.mp3", "audio/mpeg", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("musicFile.mp3").toURI())));
        }

        @AfterEach
        void afterEach() throws IOException {
            FileUtils.deleteDirectory(new File("null"));
        }

        @Test
        @DisplayName("성공 케이스")
        public void successCase() {
            // given
            BDDMockito.given(musicRepository.save(any(Music.class))).willReturn(Music.builder().build());

            ServiceMusicAddRequestDto dto = getTestDto();

            // when
            boolean isSuccess = musicAddService.addMusic(dto);

            // then
            assertThat(isSuccess).isTrue();

            verify(musicRepository, times(1)).save(any(Music.class));
        }

        private ServiceMusicAddRequestDto getTestDto() {
            return ServiceMusicAddRequestDto.builder()
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