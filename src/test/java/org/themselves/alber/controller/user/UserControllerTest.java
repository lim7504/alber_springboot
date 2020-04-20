package org.themselves.alber.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.JwtTokenProvider;
import org.themselves.alber.controller.session.dto.UserLoginDto;
import org.themselves.alber.controller.user.dto.*;
import org.themselves.alber.domain.Password;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.common.UserType;
import org.themselves.alber.repository.UserRepository;
import org.themselves.alber.service.SessionService;
import org.themselves.alber.service.UserService;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    public void testJoinUser() throws Exception {

        UserJoinDto user = new UserJoinDto();
        user.setNickname("싱싱한쓰레기");
        user.setEmail("bbb@bbb");
        user.setPassword("1234abcd");

        mockMvc.perform(post("/users")
                .contentType(String.valueOf(MediaType.JSON_UTF_8))
                .accept(String.valueOf(MediaType.JSON_UTF_8))
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testJoinUserAdmin() throws Exception {

        UserJoinDto user = new UserJoinDto();

        user.setNickname("싱싱한쓰레기");
        user.setEmail("bbb@bbb");
        user.setPassword("1234asdf");

        mockMvc.perform(post("/admin/users")
                .contentType(String.valueOf(MediaType.JSON_UTF_8))
                .accept(String.valueOf(MediaType.JSON_UTF_8))
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    public void testGetUserByNickname() {
        Optional<User> user = userRepository.findByNickname("싱싱한쓰레기");
        assertEquals(Optional.empty(),user);
    }

    @Test
    public void testGetUserOne() throws Exception{
        Optional<User> optionalUser = userRepository.findByNickname("둘리");
        String token = jwtTokenProvider.createToken(optionalUser.get().getEmail(), optionalUser.get().getType().name());

        mockMvc.perform(get("/users/detail")
                        .accept(String.valueOf(MediaType.JSON_UTF_8))
                        .header("X-AUTH-TOKEN", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.email").value("aaa@aaa"))
                .andExpect(jsonPath("data.nickname").value("둘리"))
                .andDo(print());

    }

    @Test
    public void testGetUserForMyPage() throws Exception{
        Optional<User> optionalUser = userRepository.findByNickname("둘리");
        String token = jwtTokenProvider.createToken(optionalUser.get().getEmail(), optionalUser.get().getType().name());

        mockMvc.perform(get("/users/mypage")
                .accept(String.valueOf(MediaType.JSON_UTF_8))
                .header("X-AUTH-TOKEN", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.email").value("aaa@aaa"))
                .andExpect(jsonPath("data.nickname").value("둘리"))
                .andExpect(jsonPath("data.grade").value("평범한시민"))
                .andDo(print());
    }

    @Test
    public void testDuplicationCheckNickName() throws Exception {
        UserNicknameDto userNicknameDto = new UserNicknameDto();
        userNicknameDto.setNickname("둘리");

        mockMvc.perform(get("/users/nickname")
                        .contentType(String.valueOf(MediaType.JSON_UTF_8))
                        .accept(String.valueOf(MediaType.JSON_UTF_8))
                        .content(objectMapper.writeValueAsString(userNicknameDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").value("true"))
                .andDo(print());
    }

    @Test
    public void testDuplicationCheckEmail() throws Exception {
        UserEmailDto userEmailDto = new UserEmailDto();
        userEmailDto.setEmail("aaa@aaa");

        mockMvc.perform(get("/users/email")
                        .contentType(String.valueOf(MediaType.JSON_UTF_8))
                        .accept(String.valueOf(MediaType.JSON_UTF_8))
                        .content(objectMapper.writeValueAsString(userEmailDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").value("true"))
                .andDo(print());
    }

    @Test
    @Disabled //Update 스펙 변경
    public void testUpdateUser() throws Exception{
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setNickname("길동");
        userUpdateDto.setPassword("1234");

        Optional<User> optionalUser = userRepository.findByEmail("aaa@aaa");
        String token = jwtTokenProvider.createToken(optionalUser.get().getEmail(), optionalUser.get().getType().name());

        mockMvc.perform(put("/users/detail")
                .contentType(String.valueOf(MediaType.JSON_UTF_8))
                .accept(String.valueOf(MediaType.JSON_UTF_8))
                .header("X-AUTH-TOKEN", token)
                .content(objectMapper.writeValueAsString(userUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.email").value("aaa@aaa"))
                .andExpect(jsonPath("data.nickname").value("길동"))
                .andDo(print());
    }

    @Test
    @Disabled //Update 스펙 변경
    public void testUpdateUserAdmin() throws Exception{
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setNickname("길동");
        userUpdateDto.setPassword("1234");

        Optional<User> optionalUser = userRepository.findByEmail("aaa@aaa");
        String token = jwtTokenProvider.createToken(optionalUser.get().getEmail(), optionalUser.get().getType().name());

        mockMvc.perform(put("/admin/users/1")
                .contentType(String.valueOf(MediaType.JSON_UTF_8))
                .accept(String.valueOf(MediaType.JSON_UTF_8))
                .header("X-AUTH-TOKEN", token)
                .content(objectMapper.writeValueAsString(userUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.email").value("TestUser0@alber.org"))
                .andExpect(jsonPath("data.nickname").value("길동"))
                .andDo(print());
    }

    @Test
    public void testUpdateNickName() throws Exception{
        Optional<User> optionalUser = userRepository.findByEmail("aaa@aaa");
        String token = jwtTokenProvider.createToken(optionalUser.get().getEmail(), optionalUser.get().getType().name());

        UserNicknameDto userNicknameDto = new UserNicknameDto();
        userNicknameDto.setNickname("싱싱한쓰레기");

        mockMvc.perform(patch("/users/detail/nickname")
                .contentType(String.valueOf(MediaType.JSON_UTF_8))
                .accept(String.valueOf(MediaType.JSON_UTF_8))
                .header("X-AUTH-TOKEN", token)
                .content(objectMapper.writeValueAsString(userNicknameDto)))
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals("싱싱한쓰레기", userRepository.findByEmail("aaa@aaa").get().getNickname());
    }

    @Test
    public void testUpdatePassword() throws Exception{
        Optional<User> optionalUser = userRepository.findByEmail("aaa@aaa");
        String token = jwtTokenProvider.createToken(optionalUser.get().getEmail(), optionalUser.get().getType().name());

        UserPasswordDto userPasswordDto = new UserPasswordDto();
        userPasswordDto.setPassword("1111aaaa");

        mockMvc.perform(patch("/users/detail/password")
                .contentType(String.valueOf(MediaType.JSON_UTF_8))
                .accept(String.valueOf(MediaType.JSON_UTF_8))
                .header("X-AUTH-TOKEN", token)
                .content(objectMapper.writeValueAsString(userPasswordDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Disabled //image경로가 다르기 때문에 유닛 테스트 할때만 사용
    public void testUpdateImage() throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail("aaa@aaa2");
        String token = jwtTokenProvider.createToken(optionalUser.get().getEmail(), optionalUser.get().getType().name());

        Path path = Paths.get("image/wastebasket_1/7fd70c0ddf264079a95b3f69e8ec0e63.png");
        String name = "file"; //이게 중요 이게 파라미터 이름임... 몇시간을 찾았네...
        String originalFileName = "a.png";
        String contentType = "image/png";
        byte[] content = null;
        content = Files.readAllBytes(path);

        MockMultipartFile file = new MockMultipartFile(name, originalFileName, contentType, content);

        mockMvc.perform(multipart("/users/detail/image")
                .file(file)
                .header("X-AUTH-TOKEN", token))
                .andExpect(status().isOk())
                .andDo(print());
    }
}






//        SessionService를 MockBean으로 사용할때 사용
//        @MockBean
//        SessionService sessionService;
//        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
//                .username("aaa@aaa")
//                .password("1111")
//                .roles("ADMIN")
//                .build();
//
//        Mockito.when(sessionService.loadUserByUsername("aaa@aaa")).thenReturn(userDetails);