package org.themselves.alber.controller.wastebasket.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WastebasketUpdateDto {

    @ApiModelProperty("서초역 쓰레기통")
    private String boxName;

    @ApiModelProperty("서초역앞")
    private String areaDesc;

    @ApiModelProperty("위도")
    private String latitude;

    @ApiModelProperty("경도")
    private String longitude;

    @ApiModelProperty("쓰레기통 이미지 ID 최대 3개")
    private List<String> imageList = new ArrayList<>();
}
