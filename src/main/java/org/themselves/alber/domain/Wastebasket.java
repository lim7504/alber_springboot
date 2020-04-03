package org.themselves.alber.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.themselves.alber.domain.common.BaseEntity;
import org.themselves.alber.domain.common.GarType;

import javax.persistence.*;
import java.util.ArrayList;
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
    @OneToMany(mappedBy = "wastebasket", orphanRemoval = true)
    private List<WastebasketImage> wastebasketImageList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "wastebasket")
    private List<WastebasketComment> wastebasketCommentList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;
}
