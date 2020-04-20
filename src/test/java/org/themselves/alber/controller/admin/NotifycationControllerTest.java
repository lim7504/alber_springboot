package org.themselves.alber.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.JwtTokenProvider;
import org.themselves.alber.controller.notifycation.dto.NotifycationDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.repository.NotifycationRepository;
import org.themselves.alber.repository.UserRepository;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class NotifycationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    NotifycationRepository notifycationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    public void testAddNotifycation() throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail("aaa@aaa");
        String token = jwtTokenProvider.createToken(optionalUser.get().getEmail(), optionalUser.get().getType().name());

//        if (token != null && jwtTokenProvider.validateToken(token)) {
//            Authentication auth = jwtTokenProvider.getAuthoentication(token);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }

        NotifycationDto notiDto = new NotifycationDto();
        notiDto.setTitle("공지 Test");
        notiDto.setContents("공지 내용 Test");
        notiDto.getImageList().add("10");
        notiDto.getImageList().add("11");

        mockMvc.perform(post("/admin/notifycation")
                .contentType(String.valueOf(MediaType.JSON_UTF_8))
                .accept(String.valueOf(MediaType.JSON_UTF_8))
                .header("X-AUTH-TOKEN", token)
                .content(objectMapper.writeValueAsString(notiDto)))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    public void testGetNotifycation() throws Exception{
        Optional<User> optionalUser = userRepository.findByEmail("aaa@aaa");
        String token = jwtTokenProvider.createToken(optionalUser.get().getEmail(), optionalUser.get().getType().name());

        mockMvc.perform(get("/admin/notifycation/1")
                .accept(String.valueOf(MediaType.JSON_UTF_8))
                .header("X-AUTH-TOKEN", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.notiTitle").value("타이틀"))
                .andExpect(jsonPath("data.notiContents").value("내용"))
                .andDo(print());

    }

}