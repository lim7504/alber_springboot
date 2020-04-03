package org.themselves.alber.controller.wastebasketcomment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WastebasketCommentForMyRegistCommentDto {

    private String contents;
    private String boxName;
    private String areaSi;
    private String image;

    public WastebasketCommentForMyRegistCommentDto() {
    }


    @Override
    public String toString() {
        return "WastebasketCommentNImageDto{" +
                "contents='" + contents + '\'' +
                ", boxName='" + boxName + '\'' +
                ", areaSi='" + areaSi + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
