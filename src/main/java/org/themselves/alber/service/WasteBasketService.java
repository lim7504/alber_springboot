package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.wastebasket.WastebasketDto;
import org.themselves.alber.domain.*;
import org.themselves.alber.domain.common.GarType;
import org.themselves.alber.repository.ImageRepository;
import org.themselves.alber.repository.PinRepository;
import org.themselves.alber.repository.WastebasketImageRepository;
import org.themselves.alber.repository.WastebasketRepository;
import org.themselves.alber.util.FileUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WasteBasketService {

    private final WastebasketRepository wastebasketRepository;
    private final PinRepository pinRepository;
    private final ImageRepository imageRepository;
    private final WastebasketImageRepository wastebasketImageRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void addWastebasket(Wastebasket wastebasket, User user, MultipartFile[] files) {

        //파일이 3개 이상이면??
        if(files.length > 3) {
            throw new CustomException(StatusCode.FILE_TO_MUCH_ERROR);
        }
        wastebasket.setGarType(GarType.Standard); //쓰레기통 종류 사용은 보류
        wastebasket.setUserRegisterYn(Boolean.TRUE);

        //쓰레기통 저장
        Wastebasket newWastebaseket = wastebasketRepository.save(wastebasket);

        //핀저장
        Pin pin = new Pin();
        pin.setUser(user);
        pin.setWastebasket(newWastebaseket);
        pinRepository.save(pin);

        Path rootPath = FileUtil.directoryExistAndCreate("image");
        Path childPath = FileUtil.directoryExistAndCreate(rootPath.toString()
                + File.separator + "wastebasket_" + newWastebaseket.getId());

        for(MultipartFile file : files) {

            //파일 형식이 이미지가 아니면?
            if(!FileUtil.fileEqImage(file))
                throw new CustomException(StatusCode.FILE_NOT_IMAGE_ERROR);

            //이미지저장
            Image image = new Image();
            image.setUrl(childPath.toString() + File.separator + file.getOriginalFilename());
            imageRepository.save(image);

            //쓰레기통 이미지 저장
            WastebasketImage wi = new WastebasketImage();
            wi.setImage(image);
            wi.setWastebasket(newWastebaseket);
            wastebasketImageRepository.save(wi);

            try {
                Files.copy(file.getInputStream(), new File(image.getUrl()).toPath());
            }catch (Exception e){
                throw new CustomException(StatusCode.FILE_CREATE_ERROR);
            }
        }

    }

    public WastebasketDto getWastebasketOne(Long id) {

        Optional<Wastebasket> wastebasket = wastebasketRepository.findById(id);
        if (!wastebasket.isPresent())
            throw new CustomException(StatusCode.WASTEBASKET_NOT_FOUND);

        WastebasketDto wastebasketDto = modelMapper.map(wastebasket.get(), WastebasketDto.class);

        List<WastebasketImage> wastebasketImages = wastebasketImageRepository.findByWastebaskets(id);
        for(WastebasketImage wastebasketImage : wastebasketImages){
            Optional<Image> image = imageRepository.findById(wastebasketImage.getImage().getId());
            if (!image.isPresent())
                throw new CustomException(StatusCode.INTERNAL_SERVER_ERROR);

            wastebasketDto.getImageList().add(image.get().getUrl());
        }
        return wastebasketDto;
    }
}
