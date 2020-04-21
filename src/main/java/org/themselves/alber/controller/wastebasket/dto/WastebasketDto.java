package org.themselves.alber.controller.wastebasket.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WastebasketDto {

    @ApiModelProperty("쓰레기통 ID")
    private Long id;

    @ApiModelProperty("서초역 쓰레기통")
    private String boxName;

    @ApiModelProperty("서초역앞")
    private String areaDesc;

    @ApiModelProperty("서울시")
    private String areaSi;

    @ApiModelProperty("서초동")
    private String areaGu;

    @ApiModelProperty("서초구")
    private String areaDong;

    @ApiModelProperty("위도")
    private String latitude;

    @ApiModelProperty("경도")
    private String longitude;

    @ApiModelProperty("쓰레기통 이미지 url 리스트")
    private List<String> urlList = new ArrayList<>();

    @ApiModelProperty("댓글 리스트")
    private List<WastebasketCommentRequestDto> commentDtoList = new ArrayList<>();
}
