package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.notifycation.dto.NotifycationDto;
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
    public void addNotifycation(NotifycationDto notiDto) {
        Notifycation noti = Notifycation.createNotifycation(
                  notiDto.getTitle()
                , notiDto.getContents()
                , imageMapper.mapping(notiDto.getImageList()));

        notifycationRepository.save(noti);
    }

    public Notifycation getNotifycation(long id) {
        Optional<Notifycation> optionalNoti = notifycationRepository.findById(id);
        if(!optionalNoti.isPresent())
            throw new CustomException(StatusCode.NOTIFYCATION_NOT_FOUND);

        return optionalNoti.get();
    }

    public Page<Notifycation> getNotifycationList(Pageable pageable) {
        return notifycationRepository.findAll(pageable);
    }

    @Transactional
    public void updateNotifycation(Long notiId, NotifycationDto notiDto) {
        Optional<Notifycation> optionalNoti = notifycationRepository.findById(notiId);
        if(!optionalNoti.isPresent())
            throw new CustomException(StatusCode.NOTIFYCATION_NOT_FOUND);

        optionalNoti.get().updateNotifycation(
                notiDto.getTitle()
                ,notiDto.getContents()
                ,imageMapper.mapping(notiDto.getImageList()));
    }

    @Transactional
    public void deleteNotifycation(long notiId) {
        notifycationRepository.deleteById(notiId);
        Optional<Notifycation> noti = notifycationRepository.findById(notiId);
        if (noti.isPresent())
            throw new CustomException(StatusCode.DELETE_FAIL);
    }
}
