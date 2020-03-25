package org.themselves.alber.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.themselves.alber.domain.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
class SessionServiceTest {

    @Autowired
    SessionService sessionService;

    @Test
    public void testLogin() {
        User user = new User();
        user.setEmail("aaa@aaa");
        user.setPassword("1111");
        User loginUser = sessionService.login(user);

        assertEquals("둘리", loginUser.getNickname());
    }
}