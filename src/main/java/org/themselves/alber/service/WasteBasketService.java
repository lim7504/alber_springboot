package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.themselves.alber.domain.*;
import org.themselves.alber.repository.ImageRepository;
import org.themselves.alber.repository.PinRepository;
import org.themselves.alber.repository.WastebasketImageRepository;
import org.themselves.alber.repository.WastebasketRepository;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WasteBasketService {

    private final WastebasketRepository wastebasketRepository;
    private final PinRepository pinRepository;
    private final ImageRepository imageRepository;
    private final WastebasketImageRepository wastebasketImageRepository;

    @Transactional
    public void addWastebasket(Wastebasket wastebasket, User user, MultipartFile[] files) {

        wastebasket.setGarType(GarType.Standard); //쓰레기통 종류를 넣을것인가 말것인가
        wastebasket.setUserRegisterYn(Boolean.TRUE);

        //예외처리 무슨 예외가 있을까???? 곰곰히 생각해보자.......
        Wastebasket newWastebaseket = wastebasketRepository.save(wastebasket);

        Pin pin = new Pin();
        pin.setUser(user);
        pin.setWastebasket(newWastebaseket);
        pinRepository.save(pin);


        File imageRootFolder = new File("image");
        if(!imageRootFolder.exists())
            imageRootFolder.mkdir();

        new File("image/" + "wastebasket_" + newWastebaseket.getId()).mkdir();

//        //예외처리 무슨 예외가 있을까???? 곰곰히 생각해보자.......


        //파일이 같은 이름이면?????
        List<Image> images = new ArrayList<>();
        for(MultipartFile file : files) {

            Image image = new Image();
            image.setUrl("wastebasket_" + newWastebaseket.getId() + "/" + file.getOriginalFilename());
            imageRepository.save(image);
            images.add(image);

            WastebasketImage wi = new WastebasketImage();
            wi.setImage(image);
            wi.setWastebasket(newWastebaseket);
            wastebasketImageRepository.save(wi);

            try {
                //파일명 image numberring
                Files.copy(file.getInputStream()
                        , new File(imageRootFolder.toPath() + "/" + image.getUrl()).toPath());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(!images.isEmpty()) {
            for (Image image : images) {
                imageRepository.save(image);
            }
        }
        //예외처리 무슨 예외가 있을까???? 곰곰히 생각해보자.......
    }

}