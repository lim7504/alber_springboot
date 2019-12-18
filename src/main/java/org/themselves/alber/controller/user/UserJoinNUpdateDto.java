package org.themselves.alber.controller.user;

import lombok.Data;
import lombok.Setter;
import org.themselves.alber.domain.UserStatus;

@Data
public class UserJoinNUpdateDto {

    private String nickname;
    private String email;
    private String password;

    public UserJoinNUpdateDto(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}
