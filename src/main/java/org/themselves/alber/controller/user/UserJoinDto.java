package org.themselves.alber.controller.user;

import lombok.Data;
import lombok.Setter;
import org.themselves.alber.domain.UserStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserJoinDto {

    @NotEmpty
    private String nickname;

    @Email @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordCheck;

    Boolean checkPassword() {

        if (!password.equals(passwordCheck))
            return false;

        return true;
    }
}
