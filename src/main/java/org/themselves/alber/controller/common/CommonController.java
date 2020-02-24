package org.themselves.alber.controller.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.service.CommonService;

@RestController @RequiredArgsConstructor
@RequestMapping("/common")
@Api(description = "공통")
public class CommonController {

    private final CommonService commonService;

    @PostMapping(value = "/image", consumes = "multipart/form-data; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "이미지 등록")
    public ResponseEntity<ResponseContent<ImageDto>> addImage(
              @RequestParam("file") MultipartFile file) {

        Long imageId = commonService.imageFileUpload(file);
        ImageDto imageDto = new ImageDto();
        imageDto.setImageId(imageId);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, imageDto));
    }

}
