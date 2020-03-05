package org.themselves.alber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.domain.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testFindByEmail() {

        User user = new User();
        user.setEmail("aaa@aaa");
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());

        assertEquals("둘리", byEmail.get().getNickname());
    }
}