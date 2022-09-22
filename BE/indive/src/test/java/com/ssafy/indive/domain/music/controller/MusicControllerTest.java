package com.ssafy.indive.domain.music.controller;

import com.ssafy.indive.domain.music.service.MusicAddService;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicAddRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MusicController.class)
@AutoConfigureMockMvc
@DisplayName("뮤직 컨트롤러 단위 테스트")
class MusicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicAddService musicAddService;

    @Test
    @DisplayName("[음원 등록] 사용자는 음원을 등록할 수 있어야 한다.")
    public void addMusic() throws Exception {
        // given
        MockMultipartFile image = new MockMultipartFile("file", "image.png", "image/png", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("image.png").toURI())));
        MockMultipartFile musicFile = new MockMultipartFile("file", "musicFile.mp3", "audio/mpeg", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("musicFile.mp3").toURI())));

        given(musicAddService.addMusic(any(ServiceMusicAddRequestDto.class))).willReturn(true);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.multipart("/music")
                .file(image)
                .file(musicFile)
                .param("title", "제목")
                .param("lyricist", "작사가")
                .param("composer", "작곡가")
                .param("genre", "장르")
                .param("description", "설명")
                .param("lyrics", "가사")
                .param("releaseDateTime", "2022-09-20 12:00:00")
                .param("reservationDateTime", "2022-09-20 12:10:00")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .with(request -> {
                    request.setMethod("POST");
                    return request;
                }));

        // then
        verify(musicAddService, times(1)).addMusic(any(ServiceMusicAddRequestDto.class));

        actions.andExpect(content().string("true"));
        actions.andExpect(status().isOk());
    }
}