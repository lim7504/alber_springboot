package org.themselves.alber.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.themselves.alber.controller.notifycation.dto.NotifycationListRequestDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationListResponseDto;

import java.util.List;

public interface NotifycationRepositoryCustom {
    Page<NotifycationListResponseDto> findAllWithCondition(NotifycationListRequestDto notiDto, Pageable pageable);
}
