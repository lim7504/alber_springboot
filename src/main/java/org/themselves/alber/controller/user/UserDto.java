package org.themselves.alber.controller.user;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String nickname;
    private String email;
    private String majorAddress;

}
