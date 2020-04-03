package org.themselves.alber.controller.wastebasketcomment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WastebasketCommentForMyRegistWastebasketDto {

    private Long id;
    private String contents;
    private LocalDateTime createdDate;

    private String nickname;
    private String image;
}
