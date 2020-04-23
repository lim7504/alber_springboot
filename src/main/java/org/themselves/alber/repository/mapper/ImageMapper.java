package org.themselves.alber.repository.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.common.dto.ImageIdWithSortNoDto;
import org.themselves.alber.controller.common.dto.ImageWithSortNoDto;
import org.themselves.alber.domain.Image;
import org.themselves.alber.repository.ImageRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class ImageMapper {

    private final ImageRepository imageRepository;

//    public Image mapping(Long imageId) {
//        Optional<Image> optionalImage = imageRepository.findById(imageId);
//        if(!optionalImage.isPresent())
//            throw new CustomException(StatusCode.IMAGE_NOT_FOUND);
//
//        return optionalImage.get();
//    }
//
//    public List<Image> mapping(List<Long> imageIdList) {
//        return imageIdList.stream()
//                .map(this::mapping)
//                .collect(toList());
//    }

    public ImageWithSortNoDto mapping(ImageIdWithSortNoDto imageDto) {
        Image image = imageRepository.findById(imageDto.getImageId())
                .orElseThrow(()->new CustomException(StatusCode.IMAGE_NOT_FOUND));

        ImageWithSortNoDto imageSortDto = new ImageWithSortNoDto();
        imageSortDto.setImage(image);
        imageSortDto.setSortNo(imageDto.getSortNo());

        return imageSortDto;
    }

    public List<ImageWithSortNoDto> mapping(List<ImageIdWithSortNoDto> imageDtoList) {
        return imageDtoList.stream()
                .map(this::mapping)
                .collect(toList());
    }

}
