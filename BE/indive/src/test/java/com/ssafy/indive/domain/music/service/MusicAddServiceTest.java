package com.ssafy.indive.domain.music.service;

import com.ssafy.indive.domain.MockEntityFactory;
import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.music.entity.Music;
import com.ssafy.indive.domain.music.entity.Reply;
import com.ssafy.indive.domain.music.repository.MusicLikeRepository;
import com.ssafy.indive.domain.music.repository.MusicRepository;
import com.ssafy.indive.domain.music.repository.ReplyRepository;
import com.ssafy.indive.domain.music.service.dto.ServiceMusicAddRequestDto;
import com.ssafy.indive.domain.music.service.dto.ServiceReplyAddRequestDto;
import com.ssafy.indive.utils.security.factory.WithMockSecurityContextFactory;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



@ExtendWith(MockitoExtension.class)
@DisplayName("음원 추가 서비스 단위 테스트")
class MusicAddServiceTest {

    @InjectMocks
    private MusicAddService musicAddService;

    @Mock
    private MusicRepository musicRepository;

    @Mock
    private MusicLikeRepository musicLikeRepository;

    @Mock
    private ReplyRepository replyRepository;

    @Nested
    @DisplayName("[음원 등록] 사용자는 음원을 등록할 수 있어야 한다.")
    class AddMusic {
        MockMultipartFile image;
        MockMultipartFile musicFile;

        @BeforeEach
        void beforeEach() throws URISyntaxException, IOException {
            image = new MockMultipartFile("file", "image.png", "image/png", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("image.png").toURI())));

            musicFile = new MockMultipartFile("file", "musicFile.mp3", "audio/mpeg", Files.newInputStream(Paths.get(ClassLoader.getSystemResource("musicFile.mp3").toURI())));

            WithMockSecurityContextFactory.createSecurityContext();
        }

        @AfterEach
        void afterEach() throws IOException {
            FileUtils.deleteDirectory(new File("null"));
        }

        @Test
        @DisplayName("[성공 케이스]")
        public void successCase() {
            // given
            given(musicRepository.save(any(Music.class))).willReturn(Music.builder().build());

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

    @Nested
    @DisplayName("[음원 좋아요] 사용자는 음원에 좋아요를 누를 수 있다.")
    class LikeMusic {

        Long musicSeq;

        @BeforeEach
        void beforeEach() {
            musicSeq = 1L;

            WithMockSecurityContextFactory.createSecurityContext();
        }

        @Test
        @DisplayName("[성공 케이스]")
        public void success1() {
            // given
            given(musicRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.music()));

            given(musicLikeRepository.findByMusicAndLiker(any(Music.class), any(Member.class))).willReturn(Optional.empty());

            // when
            boolean success = musicAddService.likeMusic(musicSeq);

            // then
            assertThat(success).isTrue();

            verify(musicRepository, times(1)).findById(eq(1L));
            verify(musicLikeRepository, times(1)).findByMusicAndLiker(any(Music.class), any(Member.class));
        }

        @Test
        @DisplayName("[실패 케이스] 이미 좋아요가 되어있는 경우 IllegalStateExcecption을 던진다.")
        public void failure1() {
            // given
            given(musicRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.music()));

            given(musicLikeRepository.findByMusicAndLiker(any(Music.class), any(Member.class))).willReturn(Optional.of(MockEntityFactory.musicLike()));

            // when

            // then
            assertThatThrownBy(() -> musicAddService.likeMusic(musicSeq)).isInstanceOf(IllegalStateException.class);

            verify(musicRepository, times(1)).findById(eq(1L));
            verify(musicLikeRepository, times(1)).findByMusicAndLiker(any(Music.class), any(Member.class));
        }
    }

    @Nested
    @DisplayName("[댓글 등록] 사용자는 음원에 댓글을 등록할 수 있다.")
    class AddMusicReply {

        long musicSeq;

        ServiceReplyAddRequestDto dto;

        @BeforeEach
        void beforeEach() {
            musicSeq = 1L;

            dto = new ServiceReplyAddRequestDto("댓글");

            WithMockSecurityContextFactory.createSecurityContext();

        }

        @Test
        @DisplayName("[성공 케이스]")
        public void success() {
            // given
            given(musicRepository.findById(eq(1L))).willReturn(Optional.of(MockEntityFactory.music()));

            given(replyRepository.save(any(Reply.class))).will(
                    (Answer<Reply>) invocation -> {
                        Reply reply = MockEntityFactory.reply();

                        ReflectionTestUtils.setField(reply, "seq", 1L);

                        return reply;
                    }
            );

            // when
            boolean isSuccess = musicAddService.addMusicReply(musicSeq, dto);

            // then
            assertThat(isSuccess).isTrue();

            verify(musicRepository, times(1)).findById(eq(1L));
            verify(replyRepository, times(1)).save(any(Reply.class));
        }

        @Test
        @DisplayName("[실패 케이스] 입력 받은 seq에 해당하는 음원이 없는 경우")
        public void failure() {
            // given
            given(musicRepository.findById(eq(1L))).willReturn(Optional.empty());

            // when

            // then
            assertThatThrownBy(() -> musicAddService.addMusicReply(musicSeq, dto)).isInstanceOf(IllegalArgumentException.class);

            verify(musicRepository, times(1)).findById(eq(1L));
            verify(replyRepository, times(0)).save(any(Reply.class));
        }
    }

}