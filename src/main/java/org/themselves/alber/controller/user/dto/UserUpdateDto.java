package org.themselves.alber.controller.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserUpdateDto {

    private String nickname;
    private String password;
//    private String passwordCheck;

//    public Boolean checkPassword() {
//        if (!password.equals(passwordCheck))
//            return false;
//        return true;
//    }
}
