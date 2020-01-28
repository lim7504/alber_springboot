package org.themselves.alber.controller.wastebasket;

import lombok.Data;

@Data
public class WastebasketDto {

    private Long id;
    private String boxName;
    private String areaSi;
    private String areaGu;
    private String areaDong;
    private String latitude;
    private String longitude;
    private String address;
    private String garType;
    private String agency;
    private String agency_id;
    private Boolean userRegisterYn;
}
