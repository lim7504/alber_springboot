package org.themselves.alber.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@ToString(of = {"id","nickname","email"})
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDateTime lastLoginDate;

    private String majorAddress;

    private LocalDateTime joinedDate;

    private LocalDateTime exitedDate;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserPin> userPinList = new ArrayList<>();
}
