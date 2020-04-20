package org.themselves.alber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.domain.Notifycation;
import org.themselves.alber.domain.NotifycationImage;

import java.util.List;
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
    }
}