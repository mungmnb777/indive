package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicAddRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

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

    @Test
    @DisplayName("[음원 등록] 사용자는 음원을 등록할 수 있어야 한다.")
    public void addMusic() throws Exception {
        // given
        MockMultipartFile image = new MockMultipartFile("file", "image.png", "image/png", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("image.png").toURI())));
        MockMultipartFile musicFile = new MockMultipartFile("file", "musicFile.mp3", "audio/mpeg", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("musicFile.mp3").toURI())));

        ServiceMusicAddRequestDto dto = ServiceMusicAddRequestDto.builder()
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

        BDDMockito.given(musicRepository.save(any(Music.class))).willReturn(Music.builder().build());

        // when
        boolean isSuccess = musicAddService.addMusic(dto);

        // then
        assertThat(isSuccess).isTrue();

        verify(musicRepository, times(1)).save(any(Music.class));
    }
}