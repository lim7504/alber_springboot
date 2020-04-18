package org.themselves.alber.controller.wastebasket.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistWastebasketDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class WastebasketForMyRegistWastebasketDto {

    @ApiModelProperty("쓰레기통 ID")
    private Long id;

    @ApiModelProperty("서초역 쓰레기통")
    private String boxName;

    @ApiModelProperty("서초역앞")
    private String areaSi;

    @ApiModelProperty("쓰레기통 이미지")
    private String image;

    @ApiModelProperty("생성일")
    private LocalDateTime createdDate;

    @ApiModelProperty("댓글갯수")
    private Integer commentCnt;

    @ApiModelProperty("댓글리스트 - 삭제예정")
    private List<WastebasketCommentForMyRegistWastebasketDto> commentDtoList = new ArrayList<>();
}
