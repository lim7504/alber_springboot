package org.themselves.alber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.themselves.alber.controller.wastebasket.dto.WastebasketForMyRegistWastebasketDto;
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
class WastebasketRepositoryTest {

    @Autowired
    WastebasketRepository wastebasketRepository;

    @Autowired
    UserService userService;

    @Test
    public void testGetRegistWastebasketByUser() {
        User user = userService.getUserByEmail("aaa@aaa");
        List<Pin> pinList = wastebasketRepository.findByUserForMyRegistWastebasket(user);
//
//        assertNotNull(pinList.get(0).getWastebasket());
//        assertNotNull(pinList.get(0).getWastebasket().getImage().getUrl());
//        assertNotNull(pinList.get(0).getWastebasket().getWastebasketCommentList().get(0).getUser());
//        assertNotNull(pinList.get(0).getWastebasket().getWastebasketCommentList().get(0).getUser().getImage().getUrl());
    }
}