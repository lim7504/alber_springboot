package org.themselves.alber.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.user.dto.UserUpdateDto;
import org.themselves.alber.domain.common.BaseEntity;
import org.themselves.alber.domain.common.UserSocialType;
import org.themselves.alber.domain.common.UserStatus;
import org.themselves.alber.domain.common.UserType;
import org.themselves.alber.repository.UserRepository;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@TableGenerator(name="SEQ_USER", table="SEQUENCES", pkColumnValue="USER_SEQ", allocationSize=1)
@ToString(of = {"id","nickname","email"})
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_USER")
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;

    @NotEmpty
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Pin> userPinList = new ArrayList<>();

    private void encodePassword() {
        Password pwd = new Password();
        String tarPwd = pwd.encodePassword(this.password);
        this.password = tarPwd;
    }

    public void joinCheckNSetting(UserType userType) {
        this.checkPasswordPolicy();
        this.encodePassword();

        this.socialType = UserSocialType.None;
        this.lastLoginDate = LocalDateTime.now();
        this.status = UserStatus.ACTIVE;
        this.type = userType;
    }

//    public void updateCheckNSetting(UserUpdateDto userUpdateDto) {
//        this.nickname = userUpdateDto.getNickname();
//        if (!userUpdateDto.getPassword().isEmpty())
//            this.password = userUpdateDto.getPassword();
//    }

    private void checkPasswordPolicy() {
        Password pwd = new Password();
        if (!pwd.CheckPasswordPolicy(this.password))
            throw new CustomException(StatusCode.PASSWORD_POLICY_ALONG);
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
        this.checkPasswordPolicy();
        this.encodePassword();
    }

    public void updateImage(Image image) {
        this.image = image;
    }
}
