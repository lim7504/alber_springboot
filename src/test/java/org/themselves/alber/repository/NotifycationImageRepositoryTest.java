package org.themselves.alber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.domain.Image;
import org.themselves.alber.domain.Notifycation;
import org.themselves.alber.domain.NotifycationImage;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class NotifycationImageRepositoryTest {

    @Autowired
    NotifycationRepository notifycationRepository;

    @Autowired
    NotifycationImageRepository notifycationImageRepository;

    @Autowired
    ImageRepository imageRepository;

    @Test
    public void testAddNotifycationImage() {

        Optional<Notifycation> optionalNoti = notifycationRepository.findById(1L);
        Optional<Image> optionalImage = imageRepository.findById(12L);

        NotifycationImage notifycationImage = new NotifycationImage(optionalNoti.get(), optionalImage.get());
        notifycationImageRepository.save(notifycationImage);

        assertNotNull(notifycationImageRepository.findAll());
    }
}