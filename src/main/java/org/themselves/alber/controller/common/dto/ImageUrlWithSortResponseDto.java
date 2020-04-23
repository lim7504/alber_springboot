package org.themselves.alber.controller.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.themselves.alber.domain.Image;

@Data
public class ImageUrlWithSortResponseDto {

    @ApiModelProperty("이미지 url")
    String url;

    @ApiModelProperty("순서")
    Integer sortNo;

}
