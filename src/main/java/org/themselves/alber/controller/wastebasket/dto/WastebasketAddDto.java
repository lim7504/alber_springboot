package org.themselves.alber.controller.wastebasket.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WastebasketAddDto {

    private String boxName;
    private String areaDesc;
    private String latitude;
    private String longitude;
    private List<String> imageList = new ArrayList<>();
}
