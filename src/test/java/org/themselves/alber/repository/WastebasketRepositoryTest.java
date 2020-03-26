package org.themselves.alber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.themselves.alber.domain.Wastebasket;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
class WastebasketRepositoryTest {

    @Autowired
    WastebasketRepository wastebasketRepository;

}