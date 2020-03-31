package org.themselves.alber.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentNImageDto;
import org.themselves.alber.domain.User;
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
    UserRepository userRepository;

    @Test
    public void testGetWasteBasketComment() {
        Optional<User> user = userRepository.findByEmail("aaa@aaa");
        List<WastebasketCommentNImageDto> commentNImageDtoList
                = wastebasketCommentService.getWastebasketCommentListByUser(user.get());

        assertEquals(3, commentNImageDtoList.size());
    }
}