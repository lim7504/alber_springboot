package org.themselves.alber.controller.wastebasketcomment.dto;

import lombok.Data;

@Data
public class WastebasketCommentNImageDto {

    private String contents;
    private String boxName;
    private String areaSi;
    private String image;

    public WastebasketCommentNImageDto() {
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
