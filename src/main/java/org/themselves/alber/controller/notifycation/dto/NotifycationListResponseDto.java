package org.themselves.alber.controller.notifycation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.themselves.alber.domain.Notifycation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class NotifycationListResponseDto {

    @ApiModelProperty("공지사항 번호")
    private Long id;

    @ApiModelProperty("공지사항 제목")
    private String title;

    @ApiModelProperty("공지사항 내용")
    private String contents;

    @ApiModelProperty("등록자 Nickname")
    private String userNickname;

    @ApiModelProperty("등록일시")
    private LocalDateTime createdDate;

    public NotifycationListResponseDto() {
    }

    public NotifycationListResponseDto(Notifycation notifycation, String userNickname) {
        this.id = notifycation.getId();
        this.title = notifycation.getNotiTitle();
        this.contents = notifycation.getNotiContents();
        this.createdDate = notifycation.getCreatedDate();
    }

    @Override
    public String toString() {
        return "NotifycationListResponseDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
