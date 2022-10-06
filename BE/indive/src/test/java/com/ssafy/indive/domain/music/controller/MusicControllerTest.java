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
@DisplayName("뮤직 컨트롤러 단위 테스트")
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
    @DisplayName("[음원 등록] 사용자는 음원을 등록할 수 있어야 한다.")
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
        @DisplayName("[성공 케이스]")
        public void success() throws Exception {
            // given
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
                    })
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            verify(musicAddService, times(1)).addMusic(any(ServiceMusicAddRequestDto.class));

            actions.andExpect(content().string("true"));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[실패 케이스] 음원을 첨부하지 않았을 경우")
        public void failure() throws Exception {
            // given
            given(musicAddService.addMusic(any(ServiceMusicAddRequestDto.class))).willThrow(new MusicFileNotFoundException("음악 파일은 항상 첨부해야 합니다!"));

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.multipart("/music")
                    .file(image)
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
                    })
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            verify(musicAddService, times(1)).addMusic(any(ServiceMusicAddRequestDto.class));

            actions.andExpect(content().string("음악 파일은 항상 첨부해야 합니다!"));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[음원 수정] 사용자는 음원을 수정할 수 있어야 한다.")
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
        @DisplayName("[성공 케이스]")
        public void success() throws Exception {
            // given
            given(musicModifyService.modifyMusic(eq(1L), any(ServiceMusicModifyRequestDto.class))).willReturn(true);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.multipart("/music/1")
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
        @DisplayName("[실패 케이스 1] 해당 SEQ의 음원이 없는 경우")
        public void failure1() throws Exception {
            // given
            given(musicModifyService.modifyMusic(eq(1L), any(ServiceMusicModifyRequestDto.class))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.multipart("/music/1")
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
                        request.setMethod("PUT");
                        return request;
                    })
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            verify(musicModifyService, times(1)).modifyMusic(eq(1L), any(ServiceMusicModifyRequestDto.class));

            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser
        @DisplayName("[실패 케이스 2] 로그인한 유저가 음원의 주인이 아닌 경우")
        public void failure2() throws Exception {
            // given
            given(musicModifyService.modifyMusic(eq(1L), any(ServiceMusicModifyRequestDto.class))).willThrow(new NotMatchMemberException("해당 음원의 주인이 아닙니다."));

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.multipart("/music/1")
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
                        request.setMethod("PUT");
                        return request;
                    })
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            verify(musicModifyService, times(1)).modifyMusic(eq(1L), any(ServiceMusicModifyRequestDto.class));

            actions.andExpect(content().string("해당 음원의 주인이 아닙니다."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[음원 삭제] 사용자는 음원을 삭제할 수 있어야 한다.")
    class DeleteMusic {
        @Test
        @WithMockUser
        @DisplayName("[성공 케이스]")
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
        @DisplayName("[실패 케이스 1] 해당 SEQ의 음원이 없는 경우")
        public void failure1() throws Exception {
            // given
            given(musicDeleteService.deleteMusic(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            verify(musicDeleteService, times(1)).deleteMusic(eq(1L));

            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser
        @DisplayName("[실패 케이스 2] 로그인한 유저가 음원의 주인이 아닌 경우")
        public void failure2() throws Exception {
            // given
            given(musicDeleteService.deleteMusic(eq(1L))).willThrow(new NotMatchMemberException("해당 음원의 주인이 아닙니다."));

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            verify(musicDeleteService, times(1)).deleteMusic(eq(1L));

            actions.andExpect(content().string("해당 음원의 주인이 아닙니다."));
            actions.andExpect(status().isBadRequest());
        }
    }


    @Test
    @WithMockUser
    @DisplayName("[음원 세부사항 조회] 사용자는 음원의 세부사항을 조회할 수 있어야 한다.")
    public void getMusicDetails() throws Exception {
        // given
        ServiceMusicGetResponseDto dto = ServiceMusicGetResponseDto.builder()
                .musicSeq(1L)
                .artist(MockEntityFactory.member())
                .title("제목")
                .composer("작곡가")
                .lyricist("작사가")
                .genre("장르")
                .description("설명")
                .lyrics("가사")
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
    @DisplayName("[음원 리스트 조회] 사용자는 음원 리스트를 조회할 수 있어야 한다.")
    public class getMusic {

        List<ServiceMusicGetResponseDto> dtos;

        @BeforeEach
        public void beforeEach() {
            dtos = new ArrayList<>();

            ServiceMusicGetResponseDto dto = ServiceMusicGetResponseDto.builder()
                    .musicSeq(1L)
                    .artist(MockEntityFactory.member())
                    .title("제목")
                    .composer("작곡가")
                    .lyricist("작사가")
                    .genre("장르")
                    .description("설명")
                    .lyrics("가사")
                    .build();

            dtos.add(dto);
        }

        @Test
        @WithMockUser
        @DisplayName("[성공 케이스 1] 일반적인 경우")
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
        @DisplayName("[성공 케이스 2] 쿼리 파라미터를 추가한 경우")
        public void success2() throws Exception {
            // given
            given(musicReadService.getMusic(any(WebMusicGetCondition.class), any(Pageable.class))).willReturn(dtos);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music")
                    .queryParam("title", "제목")
                    .queryParam("page", "0")
                    .queryParam("size", "4")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().json(JacksonUtil.convertToJson(dtos)));
            actions.andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("[내 음원 리스트 조회] 사용자는 본인이 등록한 음원 리스트를 조회할 수 있어야 한다.")
    public class getMyMusic {

        List<ServiceMusicGetResponseDto> dtos;

        @BeforeEach
        public void beforeEach() {
            dtos = new ArrayList<>();

            ServiceMusicGetResponseDto dto = ServiceMusicGetResponseDto.builder()
                    .musicSeq(1L)
                    .artist(MockEntityFactory.member())
                    .title("제목")
                    .composer("작곡가")
                    .lyricist("작사가")
                    .genre("장르")
                    .description("설명")
                    .lyrics("가사")
                    .build();

            dtos.add(dto);
        }

        @Test
        @WithMockUser
        @DisplayName("[성공 케이스 1] 일반적인 경우")
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
        @DisplayName("[성공 케이스 2] 쿼리 파라미터를 추가한 경우")
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
    @DisplayName("[음원 좋아요] 사용자는 음원에 좋아요를 누를 수 있다.")
    public class LikeMusic {

        @Test
        @WithMockUser
        @DisplayName("[성공 케이스]")
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
        @DisplayName("[실패 케이스 1] 해당 SEQ의 음원이 없는 경우")
        public void failure1() throws Exception {
            // given
            given(musicAddService.likeMusic(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/music/1/like")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser
        @DisplayName("[실패 케이스 2] 이미 좋아요를 누른 경우")
        public void failure2() throws Exception {
            // given
            given(musicAddService.likeMusic(eq(1L))).willThrow(IllegalStateException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/music/1/like")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("이미 좋아요를 누른 회원입니다."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[좋아요 확인] 좋아요를 눌렀는지 확인할 수 있다.")
    public class IsLike {

        @Test
        @WithMockUser
        @DisplayName("[성공 케이스]")
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
        @DisplayName("[실패 케이스 1] 해당 SEQ의 음원이 없는 경우")
        public void failure1() throws Exception {
            // given
            given(musicReadService.isLike(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/like")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[좋아요 삭제] 사용자는 좋아요를 취소할 수 있다.")
    public class DeleteLike {

        @Test
        @WithMockUser
        @DisplayName("[성공 케이스]")
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
        @DisplayName("[실패 케이스 1] 해당 SEQ의 음원이 없는 경우")
        public void failure1() throws Exception {
            // given
            given(musicDeleteService.deleteLike(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1/like")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser
        @DisplayName("[실패 케이스 2] 아직 좋아요를 누르지 않은 경우")
        public void failure2() throws Exception {
            // given
            given(musicDeleteService.deleteLike(eq(1L))).willThrow(IllegalStateException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1/like")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("아직 좋아요를 누르지 않은 회원입니다."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[좋아요 개수 조회] 사용자는 음원의 좋아요 개수를 확인할 수 있습니다.")
    public class GetLikeCount {

        @Test
        @WithMockUser
        @DisplayName("[성공 케이스]")
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
        @DisplayName("[실패 케이스] 해당 SEQ의 음원이 없는 경우")
        public void failure() throws Exception {
            // given
            given(musicReadService.getLikeCount(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/like/count")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[댓글 등록] 사용자는 음원에 댓글을 등록할 수 잇다.")
    public class AddMusicReply {

        private WebReplyAddRequestDto dto;

        @BeforeEach
        void beforeEach() {
            dto = new WebReplyAddRequestDto("댓글");
        }

        @Test
        @WithMockUser
        @DisplayName("[성공 케이스]")
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
        @DisplayName("[실패 케이스] 해당 SEQ의 음원이 없는 경우")
        public void failure() throws Exception {
            // given
            given(musicAddService.addMusicReply(eq(1L), any(ServiceReplyAddRequestDto.class))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/music/1/reply")
                    .content(JacksonUtil.convertToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[댓글 조회] 사용자는 댓글을 조회할 수 있다.")
    public class GetMusicReply {

        List<ServiceReplyGetResponseDto> dtos;

        @BeforeEach
        void beforeEach() {
            dtos = new ArrayList<>();

            ServiceReplyGetResponseDto dto = new ServiceReplyGetResponseDto(1L, MockEntityFactory.member(), "댓글");

            dtos.add(dto);
        }

        @Test
        @WithMockUser
        @DisplayName("[성공 케이스]")
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
        @DisplayName("[실패 케이스] 해당 SEQ의 음원이 없는 경우")
        public void failure() throws Exception {
            // given
            given(musicReadService.getMusicReply(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/reply")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[댓글 수정] 사용자는 댓글을 수정할 수 있다.")
    public class ModifyMusicReply {

        WebReplyModifyRequestDto dto;

        @BeforeEach
        void beforeEach() {
            dto = new WebReplyModifyRequestDto("댓글");
        }

        @Test
        @WithMockUser
        @DisplayName("[성공 케이스]")
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
        @DisplayName("[실패 케이스 1] 해당 SEQ의 댓글이 없는 경우")
        public void failure1() throws Exception {
            // given
            given(musicModifyService.modifyMusicReply(eq(1L), any(ServiceReplyModifyRequestDto.class))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/music/1/reply/1")
                    .content(JacksonUtil.convertToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser
        @DisplayName("[실패 케이스 2] 로그인한 유저가 해당 댓글의 유저가 아닌 경우")
        public void failure2() throws Exception {
            // given
            given(musicModifyService.modifyMusicReply(eq(1L), any(ServiceReplyModifyRequestDto.class))).willThrow(new NotMatchMemberException("해당 댓글의 주인이 아닙니다."));

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.put("/music/1/reply/1")
                    .content(JacksonUtil.convertToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("해당 댓글의 주인이 아닙니다."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[댓글 삭제] 사용자는 댓글을 삭제할 수 있다.")
    public class DeleteMusicReply {

        @Test
        @WithMockUser
        @DisplayName("[성공 케이스]")
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
        @DisplayName("[실패 케이스 1] 해당 SEQ의 댓글이 없는 경우")
        public void failure1() throws Exception {
            // given
            given(musicDeleteService.deleteMusicReply(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1/reply/1")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser
        @DisplayName("[실패 케이스 2] 로그인한 유저가 해당 댓글의 유저가 아닌 경우")
        public void failure2() throws Exception {
            // given
            given(musicDeleteService.deleteMusicReply(eq(1L))).willThrow(new NotMatchMemberException("해당 댓글의 주인이 아닙니다."));

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.delete("/music/1/reply/1")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("해당 댓글의 주인이 아닙니다."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[음원 전송] 음원을 octet-stream 형태로 전송한다.")
    public class DownloadMusic {

        @Test
        @WithMockUser
        @DisplayName("[성공 케이스]")
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
        @DisplayName("[실패 케이스] 해당 SEQ의 음원이 없는 경우")
        public void failure() throws Exception {
            // given
            given(musicReadService.downloadMusic(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/file-download")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[앨범 커버 전송] 앨범 커버를 octet-stream 형태로 전송한다.")
    public class DownloadImage {

        @Test
        @WithMockUser
        @DisplayName("[성공 케이스]")
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
        @DisplayName("[실패 케이스] 해당 SEQ의 음원이 없는 경우")
        public void failure() throws Exception {
            // given
            given(musicReadService.downloadImage(eq(1L))).willThrow(IllegalArgumentException.class);

            // when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/music/1/image-download")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            // then
            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());
        }
    }
}