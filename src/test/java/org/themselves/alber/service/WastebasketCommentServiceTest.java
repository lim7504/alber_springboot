package org.themselves.alber.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistCommentDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.Wastebasket;
import org.themselves.alber.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class WastebasketCommentServiceTest {

    @Autowired
    WastebasketCommentService wastebasketCommentService;

    @Autowired
    WastebasketService wastebasketService;

    @Autowired
    UserService userService;

    @Test
    public void testAddComment() {
        User user = userService.getUserByEmail("aaa@aaa");
        Wastebasket wastebasket = wastebasketService.getWastebasketOne(101L);
        wastebasketCommentService.addWastebasketComment(wastebasket,101L,"pppp");
    }


//    @Test
//    public void testGetWasteBasketComment() {
//        User user = userService.getUserByEmail("aaa@aaa");
//        List<WastebasketCommentForMyRegistCommentDto> commentNImageDtoList
//                = wastebasketCommentService.getWastebasketCommentListByUser(user);
//
//        assertEquals(3, commentNImageDtoList.size());
//    }
}