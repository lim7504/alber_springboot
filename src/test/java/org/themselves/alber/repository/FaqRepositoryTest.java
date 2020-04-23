package org.themselves.alber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.controller.faq.dto.FaqDto;
import org.themselves.alber.domain.Faq;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class FaqRepositoryTest {

    @Autowired
    FaqRepository faqRepository;

    @Test
    public void addFaqRepository() {
        FaqDto dto = new FaqDto();
        dto.setQuestion("질문");
        dto.setAnswer("답변");

        Faq faq = Faq.createFaq(dto);
        faqRepository.save(faq);

        assertNotEquals(0, faqRepository.findAll().size());
    }

    @Test
    public void getFaqRepository() {
        assertNotEquals(0, faqRepository.findAll().size());
    }

}