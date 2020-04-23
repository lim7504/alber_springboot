package org.themselves.alber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.controller.common.dto.ImageWithSortNoDto;
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
        ImageWithSortNoDto imageSortDto = new ImageWithSortNoDto();
        imageSortDto.setImage(optionalImage.get());
        imageSortDto.setSortNo(2);


        NotifycationImage notifycationImage = new NotifycationImage(optionalNoti.get(), imageSortDto);
        notifycationImageRepository.save(notifycationImage);

        assertNotNull(notifycationImageRepository.findAll());
    }
}