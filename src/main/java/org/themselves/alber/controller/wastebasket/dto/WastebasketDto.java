package org.themselves.alber.controller.wastebasket.dto;

import lombok.Data;
import org.themselves.alber.controller.wastebasket.dto.WastebasketCommentDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class WastebasketDto {

    private Long id;
    private String boxName;
    private String areaDesc;
    private String areaSi;
    private String areaGu;
    private String areaDong;
    private String latitude;
    private String longitude;
    private List<String> imageUrlList = new ArrayList<>();
    private List<WastebasketCommentDto> commentDtoList = new ArrayList<>();
}
