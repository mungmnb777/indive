package com.ssafy.indive.domain.music.controller;

import com.ssafy.indive.domain.MockEntityFactory;
import com.ssafy.indive.domain.member.exception.NotMatchMemberException;
import com.ssafy.indive.domain.music.controller.dto.WebMusicGetCondition;
import com.ssafy.indive.domain.music.controller.dto.WebReplyAddRequestDto;
import com.ssafy.indive.domain.music.controller.dto.WebReplyModifyRequestDto;
import com.ssafy.indive.domain.music.exception.MusicFileNotFoundException;
import com.ssafy.indive.domain.music.service.MusicAddService;
import com.ssafy.indive.domain.music.service.MusicDeleteService;
import com.ssafy.indive.domain.music.service.MusicModifyService;
import com.ssafy.indive.domain.music.service.MusicReadService;
import com.ssafy.indive.domain.music.service.dto.*;
import com.ssafy.indive.utils.JacksonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MusicController.class)
@AutoConfigureMockMvc
@DisplayName("?????? ???????????? ?????? ?????????")
class MusicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicAddService musicAddService;

    @MockBean
    private MusicModifyService musicModifyService;

    @MockBean
    private MusicDeleteService musicDeleteService;

    @MockBean
    private MusicReadService musicReadService;

    @Nested
    @DisplayName("[?????? ??????] ???????????? ????????? ????????? ??? ????????? ??????.")
    class AddMusic {

        MockMultipartFile image;
        MockMultipartFile musicFile;

        @BeforeEach
        void beforeEach() throws URISyntaxException, IOException {
            image = new MockMultipartFile("file", "image.png", "image/png", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("image.png").toURI())));
            musicFile = new MockMultipartFile("file", "musicFile.mp3", "audio/mpeg", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("musicFile.mp3").toURI())));
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????]")
        public void success() throws Exception {
            // given
            given(musicAddService.addMusic(any(ServiceMusicAddRequestDto.class))).willReturn(true);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.multipart("/music")
                    .file(image)
                    .file(musicFile)
                    .param("title", "??????")
                    .param("lyricist", "?????????")
                    .param("composer", "?????????")
                    .param("genre", "??????")
                    .param("description", "??????")
                    .param("lyrics", "??????")
                    .param("releaseDateTime", "2022-09-20 12:00:00")
                    .param("reservationDateTime", "2022-09-20 12:10:00")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .with(request -> {
                        request.setMethod("POST");
                        return request;
                    })
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            verify(musicAddService, times(1)).addMusic(any(ServiceMusicAddRequestDto.class));

            actions.andExpect(content().string("true"));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????] ????????? ???????????? ????????? ??????")
        public void failure() throws Exception {
            // given
            given(musicAddService.addMusic(any(ServiceMusicAddRequestDto.class))).willThrow(new MusicFileNotFoundException("?????? ????????? ?????? ???????????? ?????????!"));

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.multipart("/music")
                    .file(image)
                    .param("title", "??????")
                    .param("lyricist", "?????????")
                    .param("composer", "?????????")
                    .param("genre", "??????")
                    .param("description", "??????")
                    .param("lyrics", "??????")
                    .param("releaseDateTime", "2022-09-20 12:00:00")
                    .param("reservationDateTime", "2022-09-20 12:10:00")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .with(request -> {
                        request.setMethod("POST");
                        return request;
                    })
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            verify(musicAddService, times(1)).addMusic(any(ServiceMusicAddRequestDto.class));

            actions.andExpect(content().string("?????? ????????? ?????? ???????????? ?????????!"));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[?????? ??????] ???????????? ????????? ????????? ??? ????????? ??????.")
    class ModifyMusic {
        MockMultipartFile image;
        MockMultipartFile musicFile;

        @BeforeEach
        void beforeEach() throws URISyntaxException, IOException {
            image = new MockMultipartFile("file", "image.png", "image/png", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("image.png").toURI())));
            musicFile = new MockMultipartFile("file", "musicFile.mp3", "audio/mpeg", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("musicFile.mp3").toURI())));
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????]")
        public void success() throws Exception {
            // given
            given(musicModifyService.modifyMusic(eq(1L), any(ServiceMusicModifyRequestDto.class))).willReturn(true);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.multipart("/music/1")
                    .file(image)
                    .file(musicFile)
                    .param("title", "??????")
                    .param("lyricist", "?????????")
                    .param("composer", "?????????")
                    .param("genre", "??????")
                    .param("description", "??????")
                    .param("lyrics", "??????")
                    .param("releaseDateTime", "2022-09-20 12:00:00")
                    .param("reservationDateTime", "2022-09-20 12:10:00")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .with(request -> {
                        request.setMethod("PUT");
                        return request;
                    })
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            verify(musicModifyService, times(1)).modifyMusic(eq(1L), any(ServiceMusicModifyRequestDto.class));

            actions.andExpect(content().string("true"));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 1] ?????? SEQ??? ????????? ?????? ??????")
        public void failure1() throws Exception {
            // given
            given(musicModifyService.modifyMusic(eq(1L), any(ServiceMusicModifyRequestDto.class))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.multipart("/music/1")
                    .file(image)
                    .file(musicFile)
                    .param("title", "??????")
                    .param("lyricist", "?????????")
                    .param("composer", "?????????")
                    .param("genre", "??????")
                    .param("description", "??????")
                    .param("lyrics", "??????")
                    .param("releaseDateTime", "2022-09-20 12:00:00")
                    .param("reservationDateTime", "2022-09-20 12:10:00")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .with(request -> {
                        request.setMethod("PUT");
                        return request;
                    })
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            verify(musicModifyService, times(1)).modifyMusic(eq(1L), any(ServiceMusicModifyRequestDto.class));

            actions.andExpect(content().string("?????? ?????? ?????? ??????????????????."));
            actions.andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 2] ???????????? ????????? ????????? ????????? ?????? ??????")
        public void failure2() throws Exception {
            // given
            given(musicModifyService.modifyMusic(eq(1L), any(ServiceMusicModifyRequestDto.class))).willThrow(new NotMatchMemberException("?????? ????????? ????????? ????????????."));

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.multipart("/music/1")
                    .file(image)
                    .file(musicFile)
                    .param("title", "??????")
                    .param("lyricist", "?????????")
                    .param("composer", "?????????")
                    .param("genre", "??????")
                    .param("description", "??????")
                    .param("lyrics", "??????")
                    .param("releaseDateTime", "2022-09-20 12:00:00")
                    .param("reservationDateTime", "2022-09-20 12:10:00")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .with(request -> {
                        request.setMethod("PUT");
                        return request;
                    })
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            verify(musicModifyService, times(1)).modifyMusic(eq(1L), any(ServiceMusicModifyRequestDto.class));

            actions.andExpect(content().string("?????? ????????? ????????? ????????????."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[?????? ??????] ???????????? ????????? ????????? ??? ????????? ??????.")
    class DeleteMusic {
        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????]")
        public void success() throws Exception {
            // given
            given(musicDeleteService.deleteMusic(eq(1L))).willReturn(true);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            verify(musicDeleteService, times(1)).deleteMusic(eq(1L));

            actions.andExpect(content().string("true"));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 1] ?????? SEQ??? ????????? ?????? ??????")
        public void failure1() throws Exception {
            // given
            given(musicDeleteService.deleteMusic(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            verify(musicDeleteService, times(1)).deleteMusic(eq(1L));

            actions.andExpect(content().string("?????? ?????? ?????? ??????????????????."));
            actions.andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 2] ???????????? ????????? ????????? ????????? ?????? ??????")
        public void failure2() throws Exception {
            // given
            given(musicDeleteService.deleteMusic(eq(1L))).willThrow(new NotMatchMemberException("?????? ????????? ????????? ????????????."));

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            verify(musicDeleteService, times(1)).deleteMusic(eq(1L));

            actions.andExpect(content().string("?????? ????????? ????????? ????????????."));
            actions.andExpect(status().isBadRequest());
        }
    }


    @Test
    @WithMockUser
    @DisplayName("[?????? ???????????? ??????] ???????????? ????????? ??????????????? ????????? ??? ????????? ??????.")
    public void getMusicDetails() throws Exception {
        // given
        ServiceMusicGetResponseDto dto = ServiceMusicGetResponseDto.builder()
                .musicSeq(1L)
                .artist(MockEntityFactory.member())
                .title("??????")
                .composer("?????????")
                .lyricist("?????????")
                .genre("??????")
                .description("??????")
                .lyrics("??????")
                .build();

        given(musicReadService.getMusicDetails(eq(1L))).willReturn(dto);

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        // then
        verify(musicReadService, times(1)).getMusicDetails(eq(1L));

        actions.andExpect(status().isOk());
    }

    @Nested
    @DisplayName("[?????? ????????? ??????] ???????????? ?????? ???????????? ????????? ??? ????????? ??????.")
    public class getMusic {

        List<ServiceMusicGetResponseDto> dtos;

        @BeforeEach
        public void beforeEach() {
            dtos = new ArrayList<>();

            ServiceMusicGetResponseDto dto = ServiceMusicGetResponseDto.builder()
                    .musicSeq(1L)
                    .artist(MockEntityFactory.member())
                    .title("??????")
                    .composer("?????????")
                    .lyricist("?????????")
                    .genre("??????")
                    .description("??????")
                    .lyrics("??????")
                    .build();

            dtos.add(dto);
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 1] ???????????? ??????")
        public void success() throws Exception {
            // given
            given(musicReadService.getMusic(any(WebMusicGetCondition.class), any(Pageable.class))).willReturn(dtos);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().json(JacksonUtil.convertToJson(dtos)));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 2] ?????? ??????????????? ????????? ??????")
        public void success2() throws Exception {
            // given
            given(musicReadService.getMusic(any(WebMusicGetCondition.class), any(Pageable.class))).willReturn(dtos);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music")
                    .queryParam("title", "??????")
                    .queryParam("page", "0")
                    .queryParam("size", "4")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().json(JacksonUtil.convertToJson(dtos)));
            actions.andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("[??? ?????? ????????? ??????] ???????????? ????????? ????????? ?????? ???????????? ????????? ??? ????????? ??????.")
    public class getMyMusic {

        List<ServiceMusicGetResponseDto> dtos;

        @BeforeEach
        public void beforeEach() {
            dtos = new ArrayList<>();

            ServiceMusicGetResponseDto dto = ServiceMusicGetResponseDto.builder()
                    .musicSeq(1L)
                    .artist(MockEntityFactory.member())
                    .title("??????")
                    .composer("?????????")
                    .lyricist("?????????")
                    .genre("??????")
                    .description("??????")
                    .lyrics("??????")
                    .build();

            dtos.add(dto);
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 1] ???????????? ??????")
        public void success() throws Exception {
            // given
            given(musicReadService.getMyMusic(any(Pageable.class))).willReturn(dtos);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/my-music")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().json(JacksonUtil.convertToJson(dtos)));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 2] ?????? ??????????????? ????????? ??????")
        public void success2() throws Exception {
            // given
            given(musicReadService.getMyMusic(any(Pageable.class))).willReturn(dtos);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/my-music")
                    .queryParam("page", "0")
                    .queryParam("size", "4")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().json(JacksonUtil.convertToJson(dtos)));
            actions.andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("[?????? ?????????] ???????????? ????????? ???????????? ?????? ??? ??????.")
    public class LikeMusic {

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????]")
        public void success() throws Exception {
            // given
            given(musicAddService.likeMusic(eq(1L))).willReturn(true);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/music/1/like")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("true"));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 1] ?????? SEQ??? ????????? ?????? ??????")
        public void failure1() throws Exception {
            // given
            given(musicAddService.likeMusic(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/music/1/like")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("?????? ?????? ?????? ??????????????????."));
            actions.andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 2] ?????? ???????????? ?????? ??????")
        public void failure2() throws Exception {
            // given
            given(musicAddService.likeMusic(eq(1L))).willThrow(IllegalStateException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/music/1/like")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("?????? ???????????? ?????? ???????????????."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[????????? ??????] ???????????? ???????????? ????????? ??? ??????.")
    public class IsLike {

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????]")
        public void success() throws Exception {
            // given
            given(musicReadService.isLike(eq(1L))).willReturn(true);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/like")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("true"));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 1] ?????? SEQ??? ????????? ?????? ??????")
        public void failure1() throws Exception {
            // given
            given(musicReadService.isLike(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/like")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("?????? ?????? ?????? ??????????????????."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[????????? ??????] ???????????? ???????????? ????????? ??? ??????.")
    public class DeleteLike {

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????]")
        public void success() throws Exception {
            // given
            given(musicDeleteService.deleteLike(eq(1L))).willReturn(true);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1/like")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("true"));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 1] ?????? SEQ??? ????????? ?????? ??????")
        public void failure1() throws Exception {
            // given
            given(musicDeleteService.deleteLike(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1/like")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("?????? ?????? ?????? ??????????????????."));
            actions.andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 2] ?????? ???????????? ????????? ?????? ??????")
        public void failure2() throws Exception {
            // given
            given(musicDeleteService.deleteLike(eq(1L))).willThrow(IllegalStateException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1/like")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("?????? ???????????? ????????? ?????? ???????????????."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[????????? ?????? ??????] ???????????? ????????? ????????? ????????? ????????? ??? ????????????.")
    public class GetLikeCount {

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????]")
        public void success() throws Exception {
            // given
            given(musicReadService.getLikeCount(eq(1L))).willReturn(1);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/like/count")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("1"));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????] ?????? SEQ??? ????????? ?????? ??????")
        public void failure() throws Exception {
            // given
            given(musicReadService.getLikeCount(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/like/count")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("?????? ?????? ?????? ??????????????????."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[?????? ??????] ???????????? ????????? ????????? ????????? ??? ??????.")
    public class AddMusicReply {

        private WebReplyAddRequestDto dto;

        @BeforeEach
        void beforeEach() {
            dto = new WebReplyAddRequestDto("??????");
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????]")
        public void success() throws Exception {
            // given
            given(musicAddService.addMusicReply(eq(1L), any(ServiceReplyAddRequestDto.class))).willReturn(true);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/music/1/reply")
                    .content(JacksonUtil.convertToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("true"));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????] ?????? SEQ??? ????????? ?????? ??????")
        public void failure() throws Exception {
            // given
            given(musicAddService.addMusicReply(eq(1L), any(ServiceReplyAddRequestDto.class))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/music/1/reply")
                    .content(JacksonUtil.convertToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("?????? ?????? ?????? ??????????????????."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[?????? ??????] ???????????? ????????? ????????? ??? ??????.")
    public class GetMusicReply {

        List<ServiceReplyGetResponseDto> dtos;

        @BeforeEach
        void beforeEach() {
            dtos = new ArrayList<>();

            ServiceReplyGetResponseDto dto = new ServiceReplyGetResponseDto(1L, MockEntityFactory.member(), "??????");

            dtos.add(dto);
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????]")
        public void success() throws Exception {
            // given
            given(musicReadService.getMusicReply(eq(1L))).willReturn(dtos);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/reply")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().json(JacksonUtil.convertToJson(dtos)));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????] ?????? SEQ??? ????????? ?????? ??????")
        public void failure() throws Exception {
            // given
            given(musicReadService.getMusicReply(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/reply")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("?????? ?????? ?????? ??????????????????."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[?????? ??????] ???????????? ????????? ????????? ??? ??????.")
    public class ModifyMusicReply {

        WebReplyModifyRequestDto dto;

        @BeforeEach
        void beforeEach() {
            dto = new WebReplyModifyRequestDto("??????");
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????]")
        public void success() throws Exception {
            // given
            given(musicModifyService.modifyMusicReply(eq(1L), any(ServiceReplyModifyRequestDto.class))).willReturn(true);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/music/1/reply/1")
                    .content(JacksonUtil.convertToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("true"));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 1] ?????? SEQ??? ????????? ?????? ??????")
        public void failure1() throws Exception {
            // given
            given(musicModifyService.modifyMusicReply(eq(1L), any(ServiceReplyModifyRequestDto.class))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/music/1/reply/1")
                    .content(JacksonUtil.convertToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("?????? ?????? ?????? ??????????????????."));
            actions.andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 2] ???????????? ????????? ?????? ????????? ????????? ?????? ??????")
        public void failure2() throws Exception {
            // given
            given(musicModifyService.modifyMusicReply(eq(1L), any(ServiceReplyModifyRequestDto.class))).willThrow(new NotMatchMemberException("?????? ????????? ????????? ????????????."));

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/music/1/reply/1")
                    .content(JacksonUtil.convertToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("?????? ????????? ????????? ????????????."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[?????? ??????] ???????????? ????????? ????????? ??? ??????.")
    public class DeleteMusicReply {

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????]")
        public void success1() throws Exception {
            // given
            given(musicDeleteService.deleteMusicReply(eq(1L))).willReturn(true);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1/reply/1")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("true"));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 1] ?????? SEQ??? ????????? ?????? ??????")
        public void failure1() throws Exception {
            // given
            given(musicDeleteService.deleteMusicReply(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1/reply/1")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("?????? ?????? ?????? ??????????????????."));
            actions.andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ????????? 2] ???????????? ????????? ?????? ????????? ????????? ?????? ??????")
        public void failure2() throws Exception {
            // given
            given(musicDeleteService.deleteMusicReply(eq(1L))).willThrow(new NotMatchMemberException("?????? ????????? ????????? ????????????."));

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1/reply/1")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("?????? ????????? ????????? ????????????."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[?????? ??????] ????????? octet-stream ????????? ????????????.")
    public class DownloadMusic {

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????]")
        public void success() throws Exception {
            // given
            given(musicReadService.downloadMusic(eq(1L))).willReturn(new UrlResource(ClassLoader.getSystemResource("musicFile.mp3").toURI()));

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/file-download")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(header().string("Content-Type", "audio/mpeg"));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????] ?????? SEQ??? ????????? ?????? ??????")
        public void failure() throws Exception {
            // given
            given(musicReadService.downloadMusic(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/file-download")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("?????? ?????? ?????? ??????????????????."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[?????? ?????? ??????] ?????? ????????? octet-stream ????????? ????????????.")
    public class DownloadImage {

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????]")
        public void success() throws Exception {
            // given
            given(musicReadService.downloadImage(eq(1L))).willReturn(new UrlResource(ClassLoader.getSystemResource("image.png").toURI()));

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/image-download")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(header().string("Content-Type", "image/png"));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[?????? ?????????] ?????? SEQ??? ????????? ?????? ??????")
        public void failure() throws Exception {
            // given
            given(musicReadService.downloadImage(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/image-download")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("?????? ?????? ?????? ??????????????????."));
            actions.andExpect(status().isBadRequest());
        }
    }
}