package org.themselves.alber.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.themselves.alber.domain.common.BaseEntity;
import org.themselves.alber.domain.common.UserSocialType;
import org.themselves.alber.domain.common.UserStatus;
import org.themselves.alber.domain.common.UserType;

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

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
