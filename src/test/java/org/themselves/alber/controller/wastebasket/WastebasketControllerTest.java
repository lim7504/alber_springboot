package org.themselves.alber.controller.wastebasket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.JwtTokenProvider;
import org.themselves.alber.domain.User;
import org.themselves.alber.repository.UserRepository;
import org.themselves.alber.service.UserService;

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
class WastebasketControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    public void testGetUserOne() throws Exception{
        User user = userService.getUserByEmail("aaa@aaa");
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getType().name());

        mockMvc.perform(get("/wastebasket/myregist")
                .accept(String.valueOf(MediaType.JSON_UTF_8))
                .header("X-AUTH-TOKEN", token))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("data.email").value("aaa@aaa"))
//                .andExpect(jsonPath("data.nickname").value("둘리"))
                .andDo(print());

    }
}