package org.themselves.alber.controller.notifycation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.themselves.alber.controller.common.dto.ImageUrlWithSortResponseDto;
import org.themselves.alber.domain.Notifycation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class NotifycationResponseDto {

    @ApiModelProperty("공지사항 제목")
    private String title;

    @ApiModelProperty("공지사항 내용")
    private String contents;

    @ApiModelProperty("공지사항 url 리스트")
    private List<ImageUrlWithSortResponseDto> imageList = new ArrayList<>();

    @ApiModelProperty("등록일시")
    private LocalDateTime createdDate;

    public NotifycationResponseDto(Notifycation notifycation) {
        this.title = notifycation.getNotiTitle();
        this.contents = notifycation.getNotiContents();
        notifycation.getNotifycationImageList()
                .forEach(noti-> {
                    ImageUrlWithSortResponseDto dto = new ImageUrlWithSortResponseDto();
                    dto.setUrl(noti.getImage().getUrl());
                    dto.setSortNo(noti.getSortNo());
                    this.imageList.add(dto);
                });
        this.createdDate = notifycation.getCreatedDate();
    }
}
