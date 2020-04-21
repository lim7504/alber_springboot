package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.wastebasket.dto.WastebasketForMyRegistWastebasketDto;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistWastebasketDto;
import org.themselves.alber.domain.*;
import org.themselves.alber.domain.common.GarType;
import org.themselves.alber.repository.*;
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
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final WastebasketImageRepository wastebasketImageRepository;
    private final ModelMapper modelMapper;
    private final S3Uploader s3Uploader;

    @Transactional
    public void addWastebasket(Wastebasket wastebasket, Long userId, List<String> imageIdList) {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(StatusCode.ACCOUNT_NOT_FOUND));

        //이미지가 3개 이상이면??
        if(imageIdList.size() > 3) {
            throw new CustomException(StatusCode.FILE_TO_MUCH_ERROR);
        }

        //위도 경도에 따라 행정구역을 받아오는 API 필요 아니면 클라에서 받아오면 되나?
        wastebasket.setGarType(GarType.Standard); //쓰레기통 종류 사용은 보류
        wastebasket.setUserRegisterYn(Boolean.TRUE);

        //쓰레기통 저장
        Wastebasket newWastebaseket = wastebasketRepository.save(wastebasket);

        //핀저장
        Pin pin = new Pin();
        pin.setUser(user);
        pin.setWastebasket(newWastebaseket);
        pinRepository.save(pin);

        for(String imageId : imageIdList) {

            Image image = imageRepository.findById(Long.parseLong(imageId))
                    .orElseThrow(()->new CustomException(StatusCode.IMAGE_NOT_FOUND));

            //쓰레기통 이미지 저장
            WastebasketImage wi = new WastebasketImage();
            wi.setImage(image);
            wi.setWastebasket(newWastebaseket);
            wastebasketImageRepository.save(wi);

            newWastebaseket.setImage(image);
        }
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
    public void setWastebasketOne(Wastebasket wastebasket, List<String> imageIdList) {

        //이미지가 3개 이상이면??
        if(imageIdList.size() > 3) {
            throw new CustomException(StatusCode.FILE_TO_MUCH_ERROR);
        }

        //쓰레기통 저장
        Wastebasket updateWastebasket = wastebasketRepository.findById(wastebasket.getId())
                .orElseThrow(()->new CustomException(StatusCode.WASTEBASKET_NOT_FOUND));

        updateWastebasket.setBoxName(wastebasket.getBoxName());
        updateWastebasket.setAreaDesc(wastebasket.getAreaDesc());
        updateWastebasket.setLongitude(wastebasket.getLongitude());
        updateWastebasket.setLatitude(wastebasket.getLatitude());
        wastebasketRepository.save(updateWastebasket);

        //쓰레기통 이미지 저장
        for(String imageId : imageIdList) {

            Image image = imageRepository.findById(Long.parseLong(imageId))
                    .orElseThrow(() -> new CustomException(StatusCode.IMAGE_NOT_FOUND));

            WastebasketImage wi = new WastebasketImage();
            wi.setImage(image);
            wi.setWastebasket(updateWastebasket);
            wastebasketImageRepository.save(wi);

            updateWastebasket.setImage(image);
        }
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

        //쓰레기통이미지/이미지삭제
        for (WastebasketImage wastebasketImage : wastebasket.getWastebasketImageList()){
            new File(wastebasketImage.getImage().getUrl()).delete();
            wastebasketImageRepository.delete(wastebasketImage);
            imageRepository.delete(wastebasketImage.getImage());
        }
        //쓰레기통핀삭제
        pinRepository.delete(pin);

        //쓰레기통삭제
        wastebasketRepository.delete(wastebasket);
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
