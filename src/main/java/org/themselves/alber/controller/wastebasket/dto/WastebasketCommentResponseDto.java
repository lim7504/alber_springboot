package org.themselves.alber.controller.wastebasket.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WastebasketCommentResponseDto {

    @ApiModelProperty("싱싱한쓰레기")
    private String nickname;

    @ApiModelProperty("쓰레기통이 깨끗해요~")
    private String contents;

}
