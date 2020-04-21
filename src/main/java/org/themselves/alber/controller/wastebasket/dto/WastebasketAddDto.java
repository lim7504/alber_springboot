package org.themselves.alber.controller.wastebasket.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WastebasketAddDto {

    @ApiModelProperty("서초동 쓰레기통")
    private String boxName;

    @ApiModelProperty("서초역 앞")
    private String areaDesc;

    @ApiModelProperty("위도")
    private String latitude;

    @ApiModelProperty("경도")
    private String longitude;

    @ApiModelProperty("쓰레기통 이미지 ID 최대 3개")
    private List<String> imageIdList = new ArrayList<>();
}
