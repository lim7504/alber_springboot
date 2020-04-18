package org.themselves.alber.controller.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserNicknameDto {

    @NotEmpty
    @ApiModelProperty("싱싱한쓰레기")
    private String nickname;
}
