package org.themselves.alber.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.themselves.alber.controller.common.dto.ImageSortDto;
import org.themselves.alber.domain.common.BaseCreatedEntity;
import org.themselves.alber.repository.ImageRepository;

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

    public NotifycationImage() {
    }

    public NotifycationImage(Notifycation notifycation, ImageSortDto imageSortDto) {
        this.notifycation = notifycation;
        this.image = imageSortDto.getImage();
        this.sortNo = imageSortDto.getSortNo();
    }

}
