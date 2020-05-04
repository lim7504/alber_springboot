package org.themselves.alber.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.controller.wastebasket.dto.WastebasketForMyRegistWastebasketDto;
import org.themselves.alber.domain.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class WastebasketServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    WastebasketService wastebasketService;

    @Test
    public void testGetWastebasketForMyRegist() {
        User user = userService.getUserByEmail("aaa@aaa");
        List<WastebasketForMyRegistWastebasketDto> wastebasketDtoList = wastebasketService.getPinByUserForMyRegist(user);

    }

//    @Test
//    public void testAddWastebasketWithImage(){
//
//
//
//        wastebasketService.addWastebasket();
//
//
//    }
}