package org.themselves.alber.repository.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.domain.Image;
import org.themselves.alber.repository.ImageRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class ImageMapper {

    private final ImageRepository imageRepository;

    public Image mapping(String imageId) {
        Optional<Image> optionalImage = imageRepository.findById(Long.parseLong(imageId));
        if(!optionalImage.isPresent())
            throw new CustomException(StatusCode.IMAGE_NOT_FOUND);

        return optionalImage.get();
    }

    public List<Image> mapping(List<String> imageIdList) {
        return imageIdList.stream()
                .map(this::mapping)
                .collect(toList());
    }

}
