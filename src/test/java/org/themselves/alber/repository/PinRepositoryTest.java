package org.themselves.alber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.domain.Pin;
import org.themselves.alber.domain.User;
import org.themselves.alber.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class PinRepositoryTest {

    @Autowired
    UserService userService;

    @Autowired
    PinRepository pinRepository;
    
    @Test
    public void testGetRegistWastebasketByUser() {
        User user = userService.getUserByEmail("aaa@aaa");
        List<Pin> pinList = pinRepository.findByUser(user);

        for (Pin pin : pinList) {
            System.out.println("pin.getWastebasket().getBoxName() = " + pin.getWastebasket().getBoxName());
        }

    }
}