package org.themselves.alber.controller.faq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.JwtTokenProvider;
import org.themselves.alber.controller.faq.dto.FaqDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.repository.NotifycationRepository;
import org.themselves.alber.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class FaqControllerTest {

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
    public void testGetFaqController() throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail("aaa@aaa");
        String token = jwtTokenProvider.createToken(optionalUser.get().getEmail(), optionalUser.get().getType().name());

        mockMvc.perform(get("/faq/4")
                .contentType(String.valueOf(MediaType.JSON_UTF_8))
                .accept(String.valueOf(MediaType.JSON_UTF_8))
                .header("X-AUTH-TOKEN", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.question").value("질문3"))
                .andDo(print());
    }

    @Test
    public void testGetFaqListController() throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail("aaa@aaa");
        String token = jwtTokenProvider.createToken(optionalUser.get().getEmail(), optionalUser.get().getType().name());

        mockMvc.perform(get("/faq")
                .contentType(String.valueOf(MediaType.JSON_UTF_8))
                .param("page","0")
                .param("size","3")
                .accept(String.valueOf(MediaType.JSON_UTF_8))
                .header("X-AUTH-TOKEN", token))
                .andExpect(status().isOk())
                .andDo(print());
    }

}