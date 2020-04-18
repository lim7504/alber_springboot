package org.themselves.alber.controller.user.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistCommentDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserMyPageDto {

    @ApiModelProperty("싱싱한쓰레기")
    private String nickname;

    @ApiModelProperty("aaa@aaa")
    private String email;

    @ApiModelProperty("깨끗한시민")
    private String grade;

    @ApiModelProperty("12")
    private int pinCnt;

    @ApiModelProperty("유저 이미지 url")
    private String url;

    @ApiModelProperty("내의견 리스트")
    private List<WastebasketCommentForMyRegistCommentDto> comment = new ArrayList<>();

}
