package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.domain.Image;
import org.themselves.alber.repository.ImageRepository;
import org.themselves.alber.util.S3Uploader;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final S3Uploader s3Uploader;
    private final ImageRepository imageRepository;

    public Long imageFileUpload(MultipartFile file){
        String url;
        try {
            url = s3Uploader.upload(file);
        } catch (IOException e) {
            throw new CustomException(StatusCode.FILE_CREATE_ERROR);
        }

        //이미지저장
        Image image = new Image();
        image.setUrl(url);
        Image saveImage = imageRepository.save(image);

        return saveImage.getId();
    }
}
