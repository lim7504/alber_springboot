package org.themselves.alber.controller.notifycation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.themselves.alber.domain.Notifycation;

import java.util.ArrayList;
import java.util.List;

@Data
public class NotifycationResponseDto {

    @ApiModelProperty("공지사항 제목")
    private String title;

    @ApiModelProperty("공지사항 내용")
    private String contents;

    @ApiModelProperty("공지사항 url 리스트")
    private List<String> urlList = new ArrayList<>();

    public NotifycationResponseDto(Notifycation notifycation) {
        this.title = notifycation.getNotiTitle();
        this.contents = notifycation.getNotiContents();
        notifycation.getNotifycationImageList()
                .forEach(noti-> this.urlList.add(noti.getImage().getUrl()));
    }
}
