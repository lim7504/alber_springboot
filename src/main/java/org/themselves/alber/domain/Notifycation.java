package org.themselves.alber.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.themselves.alber.controller.common.dto.ImageWithSortNoDto;
import org.themselves.alber.domain.common.BaseEntity;

import javax.persistence.*;
import java.util.*;

import static java.util.Comparator.comparing;

@Entity
@TableGenerator(name="SEQ_NOTI", table="SEQUENCES", pkColumnValue="NOTI_SEQ", allocationSize=1)
@Getter @Setter
public class Notifycation extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="SEQ_NOTI")
    @Column(name = "noti_id")
    private Long id;

    private String notiTitle;

    private String notiContents;

    @JsonIgnore
    @OneToMany(mappedBy = "notifycation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotifycationImage> notifycationImageList = new ArrayList<>();

    public static Notifycation createNotifycation(String notiTitle, String notiContents, List<ImageWithSortNoDto> imageList) {
        Notifycation noti = new Notifycation();
        noti.notiTitle = notiTitle;
        noti.notiContents = notiContents;
        imageList.forEach(noti::addNotifycationImage);
        return noti;
    }

    private void addNotifycationImage(ImageWithSortNoDto imageSortDto){
        NotifycationImage notifycationImage = NotifycationImage.createNotifycationImage(this, imageSortDto);
        this.notifycationImageList.add(notifycationImage);
    }

    public void updateNotifycation(String notiTitle, String notiContents, List<ImageWithSortNoDto> imageSortDtoList) {

        Iterator<NotifycationImage> iter = this.notifycationImageList.iterator();
        while (iter.hasNext()){
            iter.next();
            iter.remove();
        }

        this.notiTitle = notiTitle;
        this.notiContents = notiContents;
        imageSortDtoList.forEach(this::addNotifycationImage);
    }


}
