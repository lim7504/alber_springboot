package org.themselves.alber.controller.user.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.themselves.alber.domain.common.UserSocialType;
import org.themselves.alber.domain.common.UserStatus;
import org.themselves.alber.domain.common.UserType;
import org.themselves.alber.domain.common.BaseEntity;

import java.time.LocalDateTime;

@Data
public class UserDtoByAdmin extends BaseEntity {

    @ApiModelProperty("유저 Id")
    private Long id;

    @ApiModelProperty("싱싱한쓰레기")
    private String nickname;

    @ApiModelProperty("aaa@aaa")
    private String email;

    @ApiModelProperty("USER or ADMIN")
    private UserType type;

    @ApiModelProperty("ACTIVE or INACTIVE")
    private UserStatus status;

    @ApiModelProperty("서울시 서초구 서초동")
    private String majorAddress;

    @ApiModelProperty("FACEBOOK or NAVER")
    private UserSocialType socialType;

    @ApiModelProperty("마지막 접속일시")
    private LocalDateTime lastLoginDate;

    @ApiModelProperty("탈퇴일시")
    private LocalDateTime exited_date;

}
