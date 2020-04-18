package org.themselves.alber.controller.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserJoinDto {

    @NotEmpty
    @ApiModelProperty("싱싱한쓰레기")
    private String nickname;

    @Email @NotEmpty
    @ApiModelProperty("aaa@aaa")
    private String email;

    @NotEmpty
    @ApiModelProperty("1234abcd")
    private String password;

//    @NotEmpty
//    private String passwordCheck;
//
//    public Boolean checkPassword() {
//        if (!password.equals(passwordCheck))
//            return false;
//        return true;
//    }
}
