package org.themselves.alber.controller.wastebasket.dto;

import lombok.Data;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistWastebasketDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class WastebasketForMyRegistWastebasketDto {

    private Long id;
    private String boxName;
    private String areaSi;
    private String image;
    private LocalDateTime createdDate;
    private Integer commentCnt;

    private List<WastebasketCommentForMyRegistWastebasketDto> commentDtoList = new ArrayList<>();
}
