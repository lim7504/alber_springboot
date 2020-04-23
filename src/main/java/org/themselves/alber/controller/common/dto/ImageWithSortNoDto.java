package org.themselves.alber.controller.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.themselves.alber.domain.Image;

@Data
public class ImageWithSortNoDto {

    Image image;

    Integer sortNo;
}
