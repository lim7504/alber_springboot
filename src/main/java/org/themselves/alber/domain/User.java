package org.themselves.alber.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.themselves.alber.controller.user.dto.UserUpdateDto;
import org.themselves.alber.domain.common.BaseEntity;
import org.themselves.alber.domain.common.UserSocialType;
import org.themselves.alber.domain.common.UserStatus;
import org.themselves.alber.domain.common.UserType;
import org.themselves.alber.repository.UserRepository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@TableGenerator(name="SEQ_USER", table="SEQUENCES", pkColumnValue="USER_SEQ", allocationSize=1)
@ToString(of = {"id","nickname","email"})
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="SEQ_USER")
    @Column(name = "user_id")
    private Long id;

    @Column(unique=true)
    private String nickname;

    @Column(unique=true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserType type;

    @Enumerated(EnumType.STRING)
    private UserSocialType socialType;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDateTime lastLoginDate;

    private String majorAddress;

    private LocalDateTime exitedDate;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Pin> userPinList = new ArrayList<>();

    public String encodePassword(String oriPwd) {
        Password pwd = new Password();
        return pwd.encodePassword(oriPwd);
    }

    public void joinUser(UserType userType) {
        this.setSocialType(UserSocialType.None);
        this.setLastLoginDate(LocalDateTime.now());
        this.setStatus(UserStatus.ACTIVE);
        this.setPassword(this.encodePassword(this.password));
        this.setType(userType);
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        this.setNickname(userUpdateDto.getNickname());
        if (!userUpdateDto.getPassword().isEmpty())
            this.setPassword(userUpdateDto.getPassword());
    }
}
