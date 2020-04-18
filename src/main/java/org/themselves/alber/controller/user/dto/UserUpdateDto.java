package org.themselves.alber.controller.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserUpdateDto {

    @ApiModelProperty("싱싱한쓰레기")
    private String nickname;

    @ApiModelProperty("1234abcd")
    private String password;

//    private String passwordCheck;

//    public Boolean checkPassword() {
//        if (!password.equals(passwordCheck))
//            return false;
//        return true;
//    }
}
