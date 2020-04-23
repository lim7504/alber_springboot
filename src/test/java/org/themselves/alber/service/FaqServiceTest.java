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
import org.themselves.alber.controller.faq.dto.FaqAdminListRequestDto;
import org.themselves.alber.controller.faq.dto.FaqAdminListResponseDto;
import org.themselves.alber.controller.faq.dto.FaqDto;
import org.themselves.alber.controller.faq.dto.FaqListResponseDto;
import org.themselves.alber.domain.Faq;
import org.themselves.alber.repository.FaqRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class FaqServiceTest {

    @Autowired
    FaqService faqService;

    @Autowired
    FaqRepository faqRepository;

    @Test
    public void testAddFaqService() {
        FaqDto dto = new FaqDto();
        dto.setQuestion("질문22");
        dto.setAnswer("답변22");
        faqService.addFaq(dto);

        assertTrue(faqRepository.findByQuestion("질문2").isPresent());
    }

    @Test
    public void testGetFaqService() {
        Long faq_id = 4L;
        FaqDto faq = faqService.getFaq(faq_id);

        assertNotNull(faq);
    }

    @Test
    public void testGetFaqListService() {
        Pageable pageable = PageRequest.of(0, 3);
        Page<FaqListResponseDto> faqList =faqService.getFaqList(pageable);

        assertNotEquals(0, faqList.getTotalElements());
        faqList.forEach(faq ->{
            System.out.println("faq.getQuestion() = " + faq.getQuestion());
        });
    }

    @Test
    public void testGetFaqListWithConditionService() {
        Pageable pageable = PageRequest.of(0, 3);
        FaqAdminListRequestDto dto = new FaqAdminListRequestDto();
        dto.setQuestion("질문3");

        Page<FaqAdminListResponseDto> faqList =faqService.getFaqListWithCondition(dto, pageable);

        assertEquals(1, faqList.getTotalElements());
        faqList.forEach(faq ->{
            System.out.println("faq.getQuestion() = " + faq.getQuestion());
        });

    }

    @Test
    public void testUpdateFacService() {
        Long faq_id = 4L;
        FaqDto dto = new FaqDto();
        dto.setQuestion("질문3");
        dto.setAnswer("답변3");

        faqService.updateFaq(faq_id, dto);

        assertEquals("질문3",faqRepository.findById(faq_id).get().getQuestion());
    }

    @Test
    public void testDeleteFacService() {
        Long faq_id = 4L;
        faqService.delFaq(faq_id);

        assertFalse(faqRepository.findById(faq_id).isPresent());
    }

}