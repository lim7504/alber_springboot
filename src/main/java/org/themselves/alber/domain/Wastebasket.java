package org.themselves.alber.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.common.dto.ImageWithSortNoDto;
import org.themselves.alber.controller.wastebasket.dto.WastebasketAddDto;
import org.themselves.alber.domain.common.BaseEntity;
import org.themselves.alber.domain.common.GarType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Entity
@TableGenerator(name="SEQ_WASTEBASKET", table="SEQUENCES", pkColumnValue="WASTEBASKET_SEQ", allocationSize=1)
@Getter @Setter
public class Wastebasket extends BaseEntity {

    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="SEQ_WASTEBASKET")
    @Column(name = "box_id")
    private Long id;

    private String boxName;

    private String areaDesc;

    private String areaSi;

    private String areaGu;

    private String areaDong;

    private String latitude;

    private String longitude;

    @Enumerated(EnumType.STRING)
    private GarType garType;

    private String agency;

    private String agency_id;

    private Boolean userRegisterYn;

    @JsonIgnore
    @OneToMany(mappedBy = "wastebasket")
    private List<Pin> pinList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "wastebasket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WastebasketImage> wastebasketImageList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "wastebasket")
    private List<WastebasketComment> wastebasketCommentList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    public static Wastebasket createWastebasket(String boxName,
                                                String areaDesc,
                                                String longitude,
                                                String latitude,
                                                List<ImageWithSortNoDto> imageList) {

        if(imageList.size() > 3)
            throw new CustomException(StatusCode.FILE_TO_MUCH_ERROR);

        Wastebasket wastebasket = new Wastebasket();
        wastebasket.setBoxName(boxName);
        wastebasket.setAreaDesc(areaDesc);
        wastebasket.setLongitude(longitude);
        wastebasket.setLatitude(latitude);
        wastebasket.setGarType(GarType.Standard); //쓰레기통 종류 사용은 보류
        wastebasket.setUserRegisterYn(Boolean.TRUE);
        //TODO : 위도 경도에 따라 행정구역을 받아오는 API 필요 아니면 클라에서 받아오면 되나?

        imageList.forEach(wastebasket::addWastebasketImage);
        return wastebasket;
    }

    private void addWastebasketImage(ImageWithSortNoDto imageSortDto){
        WastebasketImage wastebasketImage = WastebasketImage.createWastebasketImage(this, imageSortDto);
        this.wastebasketImageList.add(wastebasketImage);
    }

    public void updateWastebasket(String boxName,
                                  String areaDesc,
                                  String longitude,
                                  String latitude,
                                  List<ImageWithSortNoDto> imageSortDtoList){
        //이미지가 3개 이상이면??
        if(imageSortDtoList.size() > 3) {
            throw new CustomException(StatusCode.FILE_TO_MUCH_ERROR);
        }

        Iterator<WastebasketImage> iter = this.wastebasketImageList.iterator();
        while (iter.hasNext()){
            iter.next();
            iter.remove();
        }

        this.boxName = boxName;
        this.areaDesc = areaDesc;
        this.longitude = longitude;
        this.latitude = latitude;
        imageSortDtoList.forEach(this::addWastebasketImage);

    }
}
