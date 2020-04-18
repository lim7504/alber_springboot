package org.themselves.alber.controller.session.dto;

import io.swagger.annotations.*;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserLoginDto {

    @Email @NotEmpty
    @ApiModelProperty("aaa@aaa")
    private String email;

    @NotEmpty
    @ApiModelProperty("1234abcd")
    private String password;

    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
