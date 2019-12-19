package org.themselves.alber.controller.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserLoginDto {

    @Email @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
