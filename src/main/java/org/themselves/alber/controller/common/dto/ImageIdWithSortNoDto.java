package org.themselves.alber.controller.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ImageIdWithSortNoDto {

    @ApiModelProperty("이미지 Id")
    Long imageId;

    @ApiModelProperty("순서")
    Integer sortNo;
}
