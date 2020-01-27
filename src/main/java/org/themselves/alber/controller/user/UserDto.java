package org.themselves.alber.controller.user;
import lombok.Data;
import org.themselves.alber.domain.UserSocialType;
import org.themselves.alber.domain.UserStatus;
import org.themselves.alber.domain.UserType;
import org.themselves.alber.domain.common.BaseEntity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;
    private String nickname;
    private String email;
    private String majorAddress;

}
