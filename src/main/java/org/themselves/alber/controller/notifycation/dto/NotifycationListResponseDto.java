package org.themselves.alber.controller.notifycation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.themselves.alber.domain.Notifycation;

import java.time.LocalDateTime;

@Data
public class NotifycationListResponseDto {

    @ApiModelProperty("공지사항 번호")
    private Long id;

    @ApiModelProperty("공지사항 제목")
    private String title;

    @ApiModelProperty("등록일시")
    private LocalDateTime createdDate;

    public NotifycationListResponseDto(Notifycation notifycation) {
        this.id = notifycation.getId();
        this.title = notifycation.getNotiTitle();
        this.createdDate = notifycation.getCreatedDate();
    }
}
