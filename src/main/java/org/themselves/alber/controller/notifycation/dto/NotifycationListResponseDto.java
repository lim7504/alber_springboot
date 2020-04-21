package org.themselves.alber.controller.notifycation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.themselves.alber.domain.Notifycation;

import java.util.ArrayList;
import java.util.List;

@Data
public class NotifycationListResponseDto {

    @ApiModelProperty("공지사항 번호")
    private Long id;

    @ApiModelProperty("공지사항 제목")
    private String title;

    public NotifycationListResponseDto(Notifycation notifycation) {
        this.id = notifycation.getId();
        this.title = notifycation.getNotiTitle();
    }
}
