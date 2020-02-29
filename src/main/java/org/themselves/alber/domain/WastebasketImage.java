package org.themselves.alber.domain;

import lombok.Getter;
import lombok.Setter;
import org.themselves.alber.domain.common.BaseCreatedEntity;

import javax.persistence.*;

@Entity
@IdClass(WastebasketImageId.class)
@Getter @Setter
public class WastebasketImage extends BaseCreatedEntity {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Wastebasket wastebasket;

    public void setWastebasket(Wastebasket wastebasket) {
        this.wastebasket = wastebasket;
        wastebasket.getWastebasketImageList().add(this);
    }

}