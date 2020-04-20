package org.themselves.alber.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.JwtTokenProvider;
import org.themselves.alber.controller.notifycation.dto.NotifycationDto;
import org.themselves.alber.domain.Notifycation;
import org.themselves.alber.repository.NotifycationRepository;

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
        NotifycationDto notiDto = new NotifycationDto();
        notiDto.setTitle("테스트 공지사항");
        notiDto.setContents("테스트 공지사항 내용");
        notifycationService.addNotifycation(notiDto);
        assertEquals("테스트 공지사항 내용"
                , notifycationRepository.findByNotiTitle("테스트 공지사항").get().getNotiContents());
    }

    @Test
    public void testAddNotifucationWithImage() {
        NotifycationDto notiDto = new NotifycationDto();
        notiDto.setTitle("테스트 공지사항");
        notiDto.setContents("테스트 공지사항 내용");
        notiDto.getImageList().add("10");
        notiDto.getImageList().add("11");

        notifycationService.addNotifycation(notiDto);

        assertEquals(2, notifycationRepository.findByNotiTitle("테스트 공지사항").get().getNotifycationImageList().size());
    }


    @Test
    public void testGetNotifycation() {
        Notifycation noti = notifycationService.getNotifycation(1L);
        assertEquals("내용", noti.getNotiContents());
    }

    @Test
    public void testGetNotifycationList() {
        Pageable pageable = PageRequest.of(0, 3);
        Page<Notifycation> notifycationList = notifycationService.getNotifycationList(pageable);
        assertEquals(3, notifycationList.getSize());
    }

    @Test
    public void testUpdateNotifycation() {
        NotifycationDto notiDto = new NotifycationDto();
        notiDto.setTitle("테스트 공지사항2");
        notiDto.setContents("테스트 공지사항 내용2");
        notiDto.getImageList().add("10");
        notiDto.getImageList().add("11");

        notifycationService.updateNotifycation(60L, notiDto);

        assertEquals("테스트 공지사항2", notifycationRepository.findById(60L).get().getNotiTitle());
    }

    @Test
    public void testDeleteNotifycation() {
        notifycationService.deleteNotifycation(61L);
        assertFalse(notifycationRepository.findById(61L).isPresent());
    }
}