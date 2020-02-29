package org.themselves.alber.controller.wastebasket.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WastebasketResponseDto {

    private Long id;
    private String boxName;
    private String areaDesc;
    private String areaSi;
    private String areaGu;
    private String areaDong;
    private String latitude;
    private String longitude;
    private List<String> imageList = new ArrayList<>();
    private List<WastebasketCommentResponseDto> commentDtoList = new ArrayList<>();
}
