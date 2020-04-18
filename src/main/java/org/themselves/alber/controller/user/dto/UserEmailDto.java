package org.themselves.alber.controller.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserEmailDto {

    @NotEmpty
    @ApiModelProperty("aaa@aaa")
    private String email;
}
