package org.themselves.alber.controller.user;
import lombok.Data;
import org.themselves.alber.domain.UserSocialType;
import org.themselves.alber.domain.UserStatus;
import org.themselves.alber.domain.UserType;
import org.themselves.alber.domain.common.BaseEntity;

import java.time.LocalDateTime;

@Data
public class UserDtoByAdmin extends BaseEntity {

    private Long id;
    private String nickname;
    private String email;
    private UserType type;
    private UserStatus status;
    private String majorAddress;
    private UserSocialType socialType;
    private LocalDateTime lastLoginDate;
    private LocalDateTime exited_date;

}
