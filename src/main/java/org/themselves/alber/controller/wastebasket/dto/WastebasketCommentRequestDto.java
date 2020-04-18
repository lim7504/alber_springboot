package org.themselves.alber.controller.wastebasket.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WastebasketCommentRequestDto {

    @ApiModelProperty("쓰레기통이 깨끗해요~")
    private String contents;

}
