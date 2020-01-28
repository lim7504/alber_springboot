package org.themselves.alber.controller.wastebasket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.themselves.alber.domain.Image;
import org.themselves.alber.domain.UserPin;
import org.themselves.alber.domain.common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class WastebasketDtoByAdd {

    private String boxName;
    private String areaSi;
    private String areaGu;
    private String areaDong;
    private String latitude;
    private String longitude;
}
