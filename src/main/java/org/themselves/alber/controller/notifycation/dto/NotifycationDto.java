package org.themselves.alber.controller.notifycation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NotifycationDto {

    @ApiModelProperty("공지사항 제목")
    private String title;

    @ApiModelProperty("공지사항 내용")
    private String contents;

    @ApiModelProperty("공지사항 이미지 리스트")
    private List<String> imageList = new ArrayList<>();
}
