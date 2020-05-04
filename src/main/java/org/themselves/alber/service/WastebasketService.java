package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.wastebasket.dto.WastebasketAddDto;
import org.themselves.alber.controller.wastebasket.dto.WastebasketForMyRegistWastebasketDto;
import org.themselves.alber.controller.wastebasket.dto.WastebasketUpdateDto;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistWastebasketDto;
import org.themselves.alber.domain.*;
import org.themselves.alber.domain.common.GarType;
import org.themselves.alber.repository.*;
import org.themselves.alber.repository.mapper.ImageMapper;
import org.themselves.alber.util.S3Uploader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WastebasketService {

    private final WastebasketRepository wastebasketRepository;
    private final PinRepository pinRepository;
    private final UserRepository userRepository;
    private final ImageMapper imageMapper;

    @Transactional
    public void addWastebasket(WastebasketAddDto wastebasketDto, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(StatusCode.ACCOUNT_NOT_FOUND));

        Wastebasket wastebasket = Wastebasket.createWastebasket(wastebasketDto.getBoxName(),
                wastebasketDto.getAreaDesc(),
                wastebasketDto.getLongitude(),
                wastebasketDto.getLatitude(),
                imageMapper.mapping(wastebasketDto.getImageList()));

        //핀저장
        Pin pin = Pin.createPin(user, wastebasket);
        pinRepository.save(pin);

    }


    public Wastebasket getWastebasketOne(Long id) {
        Wastebasket wastebasket = wastebasketRepository.findById(id)
                .orElseThrow(()->new CustomException(StatusCode.WASTEBASKET_NOT_FOUND));

        return wastebasket;
    }


    public Page<Wastebasket> getWastebasketAll(Pageable pageable){
        return wastebasketRepository.findAll(pageable);
    }


    @Transactional
    public void setWastebasketOne(Long wastebasketId, WastebasketUpdateDto wastebasketUpdateDto) {

        //쓰레기통 저장
        Wastebasket updateWastebasket = wastebasketRepository.findByWastebasketWithImage(wastebasketId)
                .orElseThrow(()->new CustomException(StatusCode.WASTEBASKET_NOT_FOUND));

        updateWastebasket.updateWastebasket(wastebasketUpdateDto.getBoxName(),
                wastebasketUpdateDto.getAreaDesc(),
                wastebasketUpdateDto.getLongitude(),
                wastebasketUpdateDto.getLatitude(),
                imageMapper.mapping(wastebasketUpdateDto.getImageList()));

        wastebasketRepository.save(updateWastebasket);
    }

    @Transactional
    public void delWastebasket(Long id, Long userId) {

        Wastebasket wastebasket = wastebasketRepository.findById(id)
                .orElseThrow(()->new CustomException(StatusCode.WASTEBASKET_NOT_FOUND));

        Pin pin = wastebasket.getPinList().get(0);

        //댓글이 있다면
        if(wastebasket.getWastebasketCommentList().size() != 0 )
            throw new CustomException(StatusCode.WASTEBASKET_NOT_DELETE_EXIST_COMMENTS);

        //본인이 올린 쓰레기통이 아니라면
        if(!userId.equals(pin.getUser().getId()))
            throw new CustomException(StatusCode.WASTEBASKET_NOT_SAME_USER);

        //쓰레기통핀삭제
        pinRepository.delete(pin);

    }

    public List<WastebasketForMyRegistWastebasketDto> getPinByUserForMyRegist(User user) {
        List<WastebasketForMyRegistWastebasketDto> wastebasketDtoList = new ArrayList<>();
        List<Pin> pinList = wastebasketRepository.findByUserForMyRegistWastebasket(user);

        for (Pin pin : pinList) {
            Wastebasket wastebasket = pin.getWastebasket();
            WastebasketForMyRegistWastebasketDto wastebasketDto = new WastebasketForMyRegistWastebasketDto();
            wastebasketDto.setId(wastebasket.getId());
            wastebasketDto.setBoxName(wastebasket.getBoxName());
            wastebasketDto.setAreaSi(wastebasket.getAreaSi());
            wastebasketDto.setCreatedDate(wastebasket.getCreatedDate());
            if (wastebasket.getImage() != null)
                wastebasketDto.setImage(wastebasket.getImage().getUrl());
            wastebasketDto.setCommentCnt(wastebasket.getWastebasketCommentList().size());

            for (WastebasketComment wastebasketComment : wastebasket.getWastebasketCommentList()) {
                WastebasketCommentForMyRegistWastebasketDto commentDto = new WastebasketCommentForMyRegistWastebasketDto();
                commentDto.setId(wastebasketComment.getId());
                commentDto.setContents(wastebasketComment.getContents());
                commentDto.setCreatedDate(wastebasketComment.getCreatedDate());
                commentDto.setNickname(wastebasketComment.getUser().getNickname());
                if(wastebasketComment.getUser().getImage() != null)
                    commentDto.setUrl(wastebasketComment.getUser().getImage().getUrl());
                wastebasketDto.getCommentDtoList().add(commentDto);
            }
            wastebasketDtoList.add(wastebasketDto);
        }
        return  wastebasketDtoList;
    }

}
