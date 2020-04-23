package org.themselves.alber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.controller.notifycation.dto.NotifycationAdminListRequestDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationAdminListResponseDto;
import org.themselves.alber.domain.Notifycation;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class NotifycationRepositoryTest {

    @Autowired
    NotifycationRepository notifycationRepository;

    @Autowired
    NotifycationImageRepository notifycationImageRepository;

    @Test
    public void testGetNotifycation() {
        Optional<Notifycation> noti = notifycationRepository.findByNotiTitle("타이틀 공지사항");
        assertEquals("내용", noti.get().getNotiContents());
    }

    @Test
    public void testGetNotifycationList() {
        Pageable pageable = PageRequest.of(0, 3);
        NotifycationAdminListRequestDto notiDto = new NotifycationAdminListRequestDto();
        notiDto.setContents("내용");
        Page<NotifycationAdminListResponseDto> allWithCondition = notifycationRepository.findAllWithCondition(notiDto, pageable);
        allWithCondition.forEach(System.out::print);
    }
}