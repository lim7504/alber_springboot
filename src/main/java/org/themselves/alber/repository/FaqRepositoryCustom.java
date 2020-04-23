package org.themselves.alber.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.themselves.alber.controller.faq.dto.FaqAdminListRequestDto;
import org.themselves.alber.controller.faq.dto.FaqAdminListResponseDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationAdminListRequestDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationAdminListResponseDto;

public interface FaqRepositoryCustom {
    Page<FaqAdminListResponseDto> findAllWithCondition(FaqAdminListRequestDto dto, Pageable pageable);
}
