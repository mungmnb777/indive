package com.ssafy.indive.domain.Member.controller;

import com.ssafy.indive.domain.member.service.MemberAddService;
import com.ssafy.indive.domain.member.service.MemberModifyService;
import com.ssafy.indive.domain.member.service.MemberReadService;
import com.ssafy.indive.domain.music.controller.MusicController;
import com.ssafy.indive.domain.music.service.MusicAddService;
import com.ssafy.indive.domain.music.service.MusicDeleteService;
import com.ssafy.indive.domain.music.service.MusicModifyService;
import com.ssafy.indive.domain.music.service.MusicReadService;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicAddRequestDto;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicGetResponseDto;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicModifyRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MusicController.class)
@AutoConfigureMockMvc
@DisplayName("멤버 컨트롤러 단위 테스트")
class MemberControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//
//    @MockBean
//    private final MemberAddService memberAddService;
//
//    @MockBean
//    private final MemberReadService memberReadService;
//
//    @MockBean
//    private final MemberModifyService memberModifyService;
//
//    @Test
//    @DisplayName("[멤버 등록] 사용자는 멤버를 등록할 수 있어야 한다.")
//
}