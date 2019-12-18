package org.themselves.alber.controller.user;
import lombok.Data;
import org.themselves.alber.domain.UserStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;
    private String nickname;
    private String email;
    private UserStatus status;
    private LocalDateTime lastLoginDate;
    private LocalDateTime joinedDate;;

    public UserDto(Long id, String nickname, String email, UserStatus status, LocalDateTime lastLoginDate, LocalDateTime joinedDate) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.status = status;
        this.lastLoginDate = lastLoginDate;
        this.joinedDate = joinedDate;
    }
}
