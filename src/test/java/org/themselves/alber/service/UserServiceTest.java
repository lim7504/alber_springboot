package org.themselves.alber.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void testExistUserNickname() {
        assertTrue(userService.existUserNickname("둘리"));
        assertFalse(userService.existUserNickname("하나"));
    }

    @Test
    public void testExistUserEmail() {
        assertTrue(userService.existUserEmail("aaa@aaa"));
        assertFalse(userService.existUserEmail("aaa@fff"));
    }
}