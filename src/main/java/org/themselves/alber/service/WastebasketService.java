package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.domain.*;
import org.themselves.alber.domain.common.GarType;
import org.themselves.alber.repository.ImageRepository;
import org.themselves.alber.repository.PinRepository;
import org.themselves.alber.repository.WastebasketImageRepository;
import org.themselves.alber.repository.WastebasketRepository;
import org.themselves.alber.util.FileUtil;
import org.themselves.alber.util.S3Uploader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WastebasketService {

    private final WastebasketRepository wastebasketRepository;
    private final PinRepository pinRepository;
    private final ImageRepository imageRepository;
    private final WastebasketImageRepository wastebasketImageRepository;
    private final ModelMapper modelMapper;
    private final S3Uploader s3Uploader;

    @Transactional
    public void addWastebasket(Wastebasket wastebasket, User user, List<String> imageList) {

        //이미지가 3개 이상이면??
        if(imageList.size() > 3) {
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

        for(String imageId : imageList) {

            Optional<Image> op_image = imageRepository.findById(Long.parseLong(imageId));
            if(!op_image.isPresent())
                throw new CustomException(StatusCode.IMAGE_NOT_FOUND);
            //쓰레기통 이미지 저장
            WastebasketImage wi = new WastebasketImage();
            wi.setImage(op_image.get());
            wi.setWastebasket(newWastebaseket);
            wastebasketImageRepository.save(wi);
        }


    }



    public Wastebasket getWastebasketOne(Long id) {

        Optional<Wastebasket> wastebasket = wastebasketRepository.findById(id);
        if (!wastebasket.isPresent())
            throw new CustomException(StatusCode.WASTEBASKET_NOT_FOUND);

        return wastebasket.get();
    }

    public Page<Wastebasket> getWastebasketAll(Pageable pageable){
        return wastebasketRepository.findAll(pageable);
    }


    @Transactional
    public void setWastebasketOne(Wastebasket wastebasket, User user, List<String> imageList) {

        //이미지가 3개 이상이면??
        if(imageList.size() > 3) {
            throw new CustomException(StatusCode.FILE_TO_MUCH_ERROR);
        }

        //쓰레기통 저장
        Optional<Wastebasket> updateWastebasket = wastebasketRepository.findById(wastebasket.getId());
        if(!updateWastebasket.isPresent())
            throw new CustomException(StatusCode.WASTEBASKET_NOT_FOUND);

        updateWastebasket.get().setBoxName(wastebasket.getBoxName());
        updateWastebasket.get().setAreaDesc(wastebasket.getAreaDesc());
        updateWastebasket.get().setLongitude(wastebasket.getLongitude());
        updateWastebasket.get().setLatitude(wastebasket.getLatitude());
        wastebasketRepository.save(updateWastebasket.get());

        //쓰레기통 이미지 저장
        for(String imageId : imageList) {

            Optional<Image> op_image = imageRepository.findById(Long.parseLong(imageId));
            if(!op_image.isPresent())
                throw new CustomException(StatusCode.IMAGE_NOT_FOUND);

            WastebasketImage wi = new WastebasketImage();
            wi.setImage(op_image.get());
            wi.setWastebasket(updateWastebasket.get());
            wastebasketImageRepository.save(wi);
        }


    }









    @Transactional
    public void delWastebasket(Long id, User user) {

        Optional<Wastebasket> wastebasket = wastebasketRepository.findById(id);
        if (!wastebasket.isPresent())
            throw new CustomException(StatusCode.WASTEBASKET_NOT_FOUND);

        Pin pin = wastebasket.get().getPinList().get(0);

        //댓글이 있다면
        if(wastebasket.get().getWastebasketCommentList().size() != 0 )
            throw new CustomException(StatusCode.WASTEBASKET_NOT_DELETE_EXIST_COMMENTS);

        //본인이 올린 쓰레기통이 아니라면
        if(!user.getId().equals(pin.getUser().getId()))
            throw new CustomException(StatusCode.WASTEBASKET_NOT_SAME_USER);

        //쓰레기통이미지/이미지삭제
        for (WastebasketImage wastebasketImage : wastebasket.get().getWastebasketImageList()){
            new File(wastebasketImage.getImage().getUrl()).delete();
            wastebasketImageRepository.delete(wastebasketImage);
            imageRepository.delete(wastebasketImage.getImage());
        }
        //쓰레기통핀삭제
        pinRepository.delete(pin);

        //쓰레기통삭제
        wastebasketRepository.delete(wastebasket.get());
    }
}
