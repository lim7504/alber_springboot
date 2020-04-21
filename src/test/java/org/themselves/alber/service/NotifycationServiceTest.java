package org.themselves.alber.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.JwtTokenProvider;
import org.themselves.alber.controller.notifycation.dto.NotifycationRequestDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationResponseDto;
import org.themselves.alber.domain.Notifycation;
import org.themselves.alber.repository.NotifycationRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class NotifycationServiceTest {

    @Autowired
    NotifycationService notifycationService;

    @Autowired
    NotifycationRepository notifycationRepository;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    public void testAddNotifycation() {
        NotifycationRequestDto notiDto = new NotifycationRequestDto();
        notiDto.setTitle("테스트");
        notiDto.setContents("테스트");
        notifycationService.addNotifycation(notiDto);
        assertEquals("테스트"
                , notifycationRepository.findByNotiTitle("테스트").get().getNotiContents());
    }

    @Test
    public void testAddNotifucationWithImage() {
        NotifycationRequestDto notiDto = new NotifycationRequestDto();
        notiDto.setTitle("테스트");
        notiDto.setContents("테스트");
        notiDto.getImageIdList().add("10");
        notiDto.getImageIdList().add("11");

        notifycationService.addNotifycation(notiDto);

        assertEquals(2, notifycationRepository.findByNotiTitle("테스트").get().getNotifycationImageList().size());
    }


    @Test
    public void testGetNotifycation() {
        NotifycationResponseDto noti = notifycationService.getNotifycation(1L);
        assertEquals("내용", noti.getContents());
    }

    @Test
    public void testGetNotifycationList() {
//        Pageable pageable = PageRequest.of(0, 3);
//        Page<NotifycationResponseDto> notifycationList = notifycationService.getNotifycationList(pageable);
//        assertEquals(3, notifycationList.getSize());
    }

    @Test
    public void testUpdateNotifycation() {
        NotifycationRequestDto notiDto = new NotifycationRequestDto();
        notiDto.setTitle("테스트 공지사항2");
        notiDto.setContents("테스트 공지사항 내용2");
        notiDto.getImageIdList().add("10");
        notiDto.getImageIdList().add("11");

        notifycationService.updateNotifycation(60L, notiDto);

        assertEquals("테스트 공지사항2", notifycationRepository.findById(60L).get().getNotiTitle());
    }

    @Test
    public void testDeleteNotifycation() {
        notifycationService.deleteNotifycation(79L);
        assertFalse(notifycationRepository.findById(79L).isPresent());
    }
}