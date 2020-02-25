package org.themselves.alber.controller.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserUpdateDto {

    private String nickname;

    @Email @NotEmpty
    private String email;
    private String password;
    private String passwordCheck;

    public UserUpdateDto(String nickname, String email, String password, String passwordCheck) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
    }
}
