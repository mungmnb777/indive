package com.ssafy.indive.domain.Member.controller;

import com.ssafy.indive.domain.member.controller.MemberController;
import com.ssafy.indive.domain.member.controller.dto.WebMemberWriteNoticeRequestDto;
import com.ssafy.indive.domain.member.entity.enumeration.Role;
import com.ssafy.indive.domain.member.service.MemberAddService;
import com.ssafy.indive.domain.member.service.MemberBlockchainService;
import com.ssafy.indive.domain.member.service.MemberModifyService;
import com.ssafy.indive.domain.member.service.MemberReadService;
import com.ssafy.indive.domain.member.service.dto.*;
import com.ssafy.indive.utils.JacksonUtil;
import com.ssafy.indive.utils.security.factory.WithMockSecurityContextFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.UrlResource;
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

import static com.ssafy.indive.utils.JacksonUtil.convertToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc
@DisplayName("멤버 컨트롤러 단위 테스트")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberAddService memberAddService;

    @MockBean
    private MemberReadService memberReadService;

    @MockBean
    private MemberModifyService memberModifyService;

    @MockBean
    private MemberBlockchainService memberBlockchainService;


    @Test
    @WithMockUser
    @DisplayName("[멤버 등록] 사용자는 멤버를 등록할 수 있어야 한다.")
    public void addMember() throws Exception {

        // given
        ServiceMemberAddRequestDto dto = ServiceMemberAddRequestDto.builder()
                .email("test@test")
                .nickname("test-name")
                .password("test-password")
                .profileMessage("test-message")
                .wallet("test-wallet")
                .build();

        given(memberAddService.addMember(any(ServiceMemberAddRequestDto.class))).willReturn(true);
        //when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/members/join")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
          );

        // then
        verify(memberAddService, times(1)).addMember(any(ServiceMemberAddRequestDto.class));

        actions.andExpect(content().string("true"));
        actions.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("동일한 이메일이 있는지 체크한다.")
    public void isDuplicated() throws Exception {
        //given
        given(memberReadService.isDuplicated(any(ServiceDuplicatedEmail.class))).willReturn(true);
        //when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/members/duplicated-email")
                        .param("email","test@test")

                .with(SecurityMockMvcRequestPostProcessors.csrf())
        );
        //then
        verify(memberReadService, times(1)).isDuplicated(any(ServiceDuplicatedEmail.class));

        actions.andExpect(content().string("true"));
        actions.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("멤버의 세부정보를 가져온다")
    public void getMemberDetails() throws Exception{
        //given


        ServiceMemberGetResponseDto responsedto = ServiceMemberGetResponseDto.builder()
                .memberSeq(1)
                .email("test@test")
                .nickname("test-nickname")
                .role(Role.ROLE_USER)
                .wallet("test-wallet")
                .profileMessage("test-profileMessage")
                .notice("test-notice")
                .build();

        Mockito.when(memberReadService.getMemberDetails(1)).thenReturn(responsedto);

        //when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/members/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        //then

        actions.andExpect(status().isOk());
        actions.andExpect(content().string(convertToJson(responsedto)));
    }

    @Nested
    @DisplayName("[음원 수정] 사용자는 개인 정보를 수정할 수 있다.")
    class ModifyMember{

        @BeforeEach
        void beforeEach() {
            WithMockSecurityContextFactory.createSecurityContext();
        }


        @Test
        @WithMockUser
        @DisplayName("[성공 케이스] 사용자는 개인 정보를 수정할 수 있다..")
        public void success() throws Exception{
            //given

            MockMultipartFile image = new MockMultipartFile("file", "image.png", "image/png", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("image.png").toURI())));
            MockMultipartFile background = new MockMultipartFile("file", "background.png", "image/png", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("background.png").toURI())));

            given(memberModifyService.modifyMember(eq(1L),any(ServiceMemberModifyRequestDto.class))).willReturn(true);
            //when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.multipart("/members/1")
                    .file(image)
                    .file(background)
                    .param("nickname","test-nickname")
                    .param("profile-message","test-profile-message")
                    .with(request -> {
                        request.setMethod("PUT");
                        return request;
                    })
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            //then
            verify(memberModifyService, times(1)).modifyMember(eq(1L), any(ServiceMemberModifyRequestDto.class));

            actions.andExpect(content().string("true"));
            actions.andExpect(status().isOk());


        }

        @Test
        @WithMockUser
        @DisplayName("[실패 케이스] 해당 SEQ의 멤버가 없는 경우")
        public void failure() throws Exception {
            MockMultipartFile image = new MockMultipartFile("file", "image.png", "image/png", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("image.png").toURI())));
            MockMultipartFile background = new MockMultipartFile("file", "background.png", "image/png", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("background.png").toURI())));

            given(memberModifyService.modifyMember(eq(1L),any(ServiceMemberModifyRequestDto.class))).willThrow(new IllegalArgumentException("요청 값을 다시 확인해주세요."));

            //when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.multipart("/members/1")
                    .file(image)
                    .file(background)
                    .param("nickname","test-nickname")
                    .param("profile-message","test-profile-message")
                    .with(request -> {
                        request.setMethod("PUT");
                        return request;
                    })
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            //then
            verify(memberModifyService, times(1)).modifyMember(eq(1L), any(ServiceMemberModifyRequestDto.class));

            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());
        }



    }



    @Test
    @WithMockUser
    @DisplayName("로그인한 멤버의 세부 사항을 확인한다.")
    public void getLoginMemberDetails() throws Exception{
        ServiceMemberGetResponseDto responsedto = ServiceMemberGetResponseDto.builder()
                .memberSeq(1)
                .email("test@test")
                .nickname("test-nickname")
                .role(Role.ROLE_USER)
                .wallet("test-wallet")
                .profileMessage("test-profileMessage")
                .notice("test-notice")
                .build();

        Mockito.when(memberReadService.getLoginMemberDetails()).thenReturn(responsedto);

        //when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/members/my-account")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        //then
        actions.andExpect(status().isOk());
        actions.andExpect(content().string(convertToJson(responsedto)));


    }

    @Test
    @WithMockUser
    @DisplayName("공지사항을 수정한다.")
    public void writeNotice() throws Exception{
        //given
        WebMemberWriteNoticeRequestDto dto = WebMemberWriteNoticeRequestDto.builder()
                        .notice("test-notice")
                        .build();

        given(memberModifyService.writeNotice(any(ServiceMemberWriteNoticeRequestDto.class), eq(1L))).willReturn(true);

        //when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/members/1/notice")
                .content(convertToJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        //then
        verify(memberModifyService, times(1)).writeNotice(any(ServiceMemberWriteNoticeRequestDto.class),eq(1L));

        actions.andExpect(content().string("true"));
        actions.andExpect(status().isOk());

    }

    @Nested
    @DisplayName("[프로필사진 다운로드]")
    public class DownloadProfile{
        @Test
        @WithMockUser
        @DisplayName("[성공 케이스] 프로필 이미지의 링크를 다운로드한다.")
        public void success() throws Exception{
            //given
            given(memberReadService.downloadProfileImage(eq(1L))).willReturn(new UrlResource(ClassLoader.getSystemResource("image.png").toURI()));

            //when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/members/1/profileimg-download")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));
            //then
            verify(memberReadService, times(1)).downloadProfileImage(eq(1L));

            actions.andExpect(header().string("Content-Type", "image/png"));
            actions.andExpect(status().isOk());
        }

        @Test
        @WithMockUser
        @DisplayName("[실패 케이스] 해당 프로필 이미지 멤버의 SEQ 가 없는 경우.")
        public void failure() throws Exception{
            //given
            given(memberReadService.downloadProfileImage(eq(1L))).willThrow(IllegalArgumentException.class);

            //when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/members/1/profileimg-download")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));
            //then
            verify(memberReadService, times(1)).downloadProfileImage(eq(1L));

            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("[배경화면 다운로드]")
    public class DownloadBackground{
    @Test
    @WithMockUser
    @DisplayName("[성공 케이스] 배경 이미지의 링크를 다운로드한다.")
    public void success() throws Exception{

        //given
        given(memberReadService.downloadBackgroundImage(eq(1L))).willReturn(new UrlResource(ClassLoader.getSystemResource("image.png").toURI()));

        //when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/members/1/backgroundimg-download")
                .with(SecurityMockMvcRequestPostProcessors.csrf()));
        //then
        verify(memberReadService,times(1)).downloadBackgroundImage(eq(1L));

        actions.andExpect(header().string("Content-Type", "image/png"));
        actions.andExpect(status().isOk());

    }

        @Test
        @WithMockUser
        @DisplayName("[실패 케이스] 해당 배경화면 이미지 멤버의 SEQ 가 없는 경우.")
        public void failure() throws Exception{

            //given
            given(memberReadService.downloadBackgroundImage(eq(1L))).willThrow(IllegalArgumentException.class);

            //when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/members/1/backgroundimg-download")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));
            //then
            verify(memberReadService,times(1)).downloadBackgroundImage(eq(1L));

            actions.andExpect(content().string("요청 값을 다시 확인해주세요."));
            actions.andExpect(status().isBadRequest());

        }
    }

    @Nested
    @DisplayName("[랭킹 조회]")
    public class GetDonationRanking{
        List<ServiceMemberDonationRankResponseDto> list;
        @BeforeEach
        public void beforeEach() {
           list = new ArrayList<>();
            ServiceMemberDonationRankResponseDto dto = ServiceMemberDonationRankResponseDto.builder()
                    .memberSeq(1L)
                    .nickname("test-nickname")
                    .address("test-address")
                    .totalValue(100)
                    .build();

            list.add(dto);
        }
        @Test
        @WithMockUser
        @DisplayName("[성공 케이스] 후원 랭킹 조회에 성공한다.")
        public void success() throws Exception{
            //given
            given(memberBlockchainService.getDonationRanking(any(String.class))).willReturn(list);

            //when
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/members/donation-rank/1")
                    .with(SecurityMockMvcRequestPostProcessors.csrf()));

            //then
            actions.andExpect(content().json(JacksonUtil.convertToJson(list)));
            actions.andExpect(status().isOk());
        }

    }

}