package org.themselves.alber.controller.user.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserResponseDto {

    @ApiModelProperty("싱싱한쓰레기")
    private String nickname;

    @ApiModelProperty("aaa@aaa")
    private String email;

    @ApiModelProperty("유저 이미지 url")
    private String url;

}
