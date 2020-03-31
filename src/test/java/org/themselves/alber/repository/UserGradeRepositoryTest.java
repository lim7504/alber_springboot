package org.themselves.alber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.domain.UserGrade;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class UserGradeRepositoryTest {

    @Autowired
    UserGradeRepository userGradeRepository;

    @Test
    public void testGetUserGrade() {
        List<UserGrade> all = userGradeRepository.findAll();
        for (UserGrade userGrade : all) {
            System.out.println("userGrade = " + userGrade);
        }
    }
}