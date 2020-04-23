package org.themselves.alber.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.repository.mapper.ImageMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class ImageMapperTest {

    @Autowired
    ImageMapper imageMapper;

    @Test
    public void testImageMapperTest() {


//        Image image = imageMapper.mapping(10L);
//        System.out.println("image = " + image.getUrl());
    }
}