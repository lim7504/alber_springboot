package org.themselves.alber.controller.wastebasketcomment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WastebasketCommentForMyRegistCommentDto {

    @ApiModelProperty("쓰레기통이 깨끗해요~")
    private String contents;

    @ApiModelProperty("서초역 쓰레기통")
    private String boxName;

    @ApiModelProperty("서초역앞")
    private String areaSi;

    @ApiModelProperty("유저 이미지 url")
    private String image;

    public WastebasketCommentForMyRegistCommentDto() {
    }


    @Override
    public String toString() {
        return "WastebasketCommentNImageDto{" +
                "contents='" + contents + '\'' +
                ", boxName='" + boxName + '\'' +
                ", areaSi='" + areaSi + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
