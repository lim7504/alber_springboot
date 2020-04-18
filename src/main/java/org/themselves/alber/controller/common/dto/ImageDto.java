package org.themselves.alber.controller.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class ImageDto {

    @ApiModelProperty("이미지 Id")
    long imageId;
}
