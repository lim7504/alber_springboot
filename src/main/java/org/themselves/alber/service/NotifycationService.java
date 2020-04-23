package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.notifycation.dto.NotifycationListRequestDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationListResponseDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationRequestDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationResponseDto;
import org.themselves.alber.repository.mapper.ImageMapper;
import org.themselves.alber.domain.Notifycation;
import org.themselves.alber.repository.NotifycationRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotifycationService {

    private final NotifycationRepository notifycationRepository;
    private final ImageMapper imageMapper;

    @Transactional
    public void addNotifycation(NotifycationRequestDto notiDto) {
        Notifycation noti = Notifycation.createNotifycation(
                  notiDto.getTitle()
                , notiDto.getContents()
                , imageMapper.mapping(notiDto.getImageList()));

        notifycationRepository.save(noti);
    }

    public NotifycationResponseDto getNotifycation(long id) {
        Notifycation noti = notifycationRepository.findByNotifycationWithImage(id)
                .orElseThrow(()-> new CustomException(StatusCode.NOTIFYCATION_NOT_FOUND));

        return new NotifycationResponseDto(noti);
    }

    public Page<NotifycationListResponseDto> getNotifycationList(
            NotifycationListRequestDto notiDto,
            Pageable pageable) {
        return notifycationRepository.findAllWithCondition(notiDto, pageable);
    }

    @Transactional
    public void updateNotifycation(Long notiId, NotifycationRequestDto notiDto) {
        Notifycation noti = notifycationRepository.findById(notiId)
                .orElseThrow(()->new CustomException(StatusCode.NOTIFYCATION_NOT_FOUND));

        noti.updateNotifycation(
                notiDto.getTitle()
                ,notiDto.getContents()
                ,imageMapper.mapping(notiDto.getImageList()));
    }

    @Transactional
    public void deleteNotifycation(long notiId) {
        notifycationRepository.deleteById(notiId);
        Optional<Notifycation> optionalNoti = notifycationRepository.findById(notiId);
        if(optionalNoti.isPresent())
            throw new CustomException(StatusCode.DELETE_FAIL);
    }
}
