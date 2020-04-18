package org.themselves.alber.controller.wastebasketcomment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WastebasketCommentForMyRegistWastebasketDto {

    @ApiModelProperty("댓글 ID")
    private Long id;

    @ApiModelProperty("쓰레기통이 깨끗해요~")
    private String contents;

    @ApiModelProperty("댓글 생성일")
    private LocalDateTime createdDate;

    @ApiModelProperty("싱싱한쓰레기")
    private String nickname;

    @ApiModelProperty("유저 이미지 url")
    private String image;
}
