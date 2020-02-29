package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.domain.Image;
import org.themselves.alber.domain.Wastebasket;
import org.themselves.alber.domain.WastebasketImage;
import org.themselves.alber.repository.ImageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public List<String> getImageUrlList(Wastebasket wastebasket) {

        List<String> urlList = new ArrayList<>();
        for(WastebasketImage wastebasketImage : wastebasket.getWastebasketImageList()){
            Optional<Image> image = imageRepository.findById(wastebasketImage.getImage().getId());
            if (!image.isPresent())
                throw new CustomException(StatusCode.INTERNAL_SERVER_ERROR);
            urlList.add(image.get().getUrl());
        }
        return urlList;
    }
}
