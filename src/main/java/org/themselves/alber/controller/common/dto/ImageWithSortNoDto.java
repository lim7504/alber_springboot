package org.themselves.alber.controller.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.themselves.alber.domain.Image;

import java.util.Objects;

@Data
public class ImageWithSortNoDto {

    Image image;

    Integer sortNo;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ImageWithSortNoDto that = (ImageWithSortNoDto) object;
        return Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image);
    }
}
