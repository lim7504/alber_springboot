package org.themselves.alber.domain;

import lombok.Getter;
import lombok.Setter;
import org.themselves.alber.controller.common.dto.ImageWithSortNoDto;
import org.themselves.alber.domain.common.BaseCreatedEntity;

import javax.persistence.*;

@Entity
@IdClass(NotifycationImageId.class)
@Getter @Setter
public class NotifycationImage extends BaseCreatedEntity {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noti_id")
    private Notifycation notifycation;

    private Integer sortNo;

    public static NotifycationImage createNotifycationImage(Notifycation notifycation, ImageWithSortNoDto imageSortDto){
        NotifycationImage notifycationImage = new NotifycationImage();
        notifycationImage.setNotifycation(notifycation);
        notifycationImage.setImage(imageSortDto.getImage());
        notifycationImage.setSortNo(imageSortDto.getSortNo());
        return notifycationImage;
    }

}
