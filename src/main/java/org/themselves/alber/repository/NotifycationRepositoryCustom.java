package org.themselves.alber.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.themselves.alber.controller.notifycation.dto.NotifycationAdminListRequestDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationAdminListResponseDto;

public interface NotifycationRepositoryCustom {
    Page<NotifycationAdminListResponseDto> findAllWithCondition(NotifycationAdminListRequestDto notiDto, Pageable pageable);
}
