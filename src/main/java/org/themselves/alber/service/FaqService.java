package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.faq.dto.FaqAdminListRequestDto;
import org.themselves.alber.controller.faq.dto.FaqAdminListResponseDto;
import org.themselves.alber.controller.faq.dto.FaqDto;
import org.themselves.alber.controller.faq.dto.FaqListResponseDto;
import org.themselves.alber.domain.Faq;
import org.themselves.alber.repository.FaqRepository;

@Service
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;

    public void addFaq(FaqDto dto) {
        Faq faq = Faq.createFaq(dto);
        faqRepository.save(faq);
    }

    public FaqDto getFaq(Long faq_id) {
        Faq faq = faqRepository.findById(faq_id)
                .orElseThrow(() -> new CustomException(StatusCode.FAQ_NOT_FOUND));

        return new FaqDto(faq);
    }

    public void updateFaq(Long faq_id, FaqDto dto) {
        Faq faq = faqRepository.findById(faq_id)
                .orElseThrow(() -> new CustomException(StatusCode.FAQ_NOT_FOUND));

        faq.updateFaq(dto);
    }

    public void delFaq(Long faq_id) {
        Faq faq = faqRepository.findById(faq_id)
                .orElseThrow(() -> new CustomException(StatusCode.FAQ_NOT_FOUND));

        faqRepository.delete(faq);
    }

    public Page<FaqListResponseDto> getFaqList(Pageable pageable) {
        return faqRepository.findAll(pageable).map(FaqListResponseDto::new);
    }

    public Page<FaqAdminListResponseDto> getFaqListWithCondition(FaqAdminListRequestDto dto, Pageable pageable) {
        return faqRepository.findAllWithCondition(dto, pageable);
    }
}
