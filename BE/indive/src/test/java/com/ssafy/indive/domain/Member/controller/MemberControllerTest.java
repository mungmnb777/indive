package com.ssafy.indive.domain.Member.controller;

import com.ssafy.indive.domain.member.controller.MemberController;
import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.entity.enumeration.Role;
import com.ssafy.indive.domain.member.service.MemberAddService;
import com.ssafy.indive.domain.member.service.MemberModifyService;
import com.ssafy.indive.domain.member.service.MemberReadService;
import com.ssafy.indive.domain.member.service.dto.ServiceMemberAddRequestDto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static com.ssafy.indive.utils.JacksonUtil.convertToJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



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

        Mockito.when(memberAddService.addMember(any(ServiceMemberAddRequestDto.class))).thenReturn(true);

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
}