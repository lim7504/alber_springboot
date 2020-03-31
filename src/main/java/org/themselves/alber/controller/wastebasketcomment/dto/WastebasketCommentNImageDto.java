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

    public WastebasketCommentNImageDto(String contents) {
        this.contents = contents;
    }

    public WastebasketCommentNImageDto(String contents, String boxName, String areaSi, String image) {
        this.contents = contents;
        this.boxName = boxName;
        this.areaSi = areaSi;
        this.image = image;
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
