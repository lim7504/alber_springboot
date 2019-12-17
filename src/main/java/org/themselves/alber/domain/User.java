package org.themselves.alber.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String email;

    private String password;

    private String majorAddress;

    private LocalDateTime joinedDate;

    private LocalDateTime exitedDate;

    @OneToMany(mappedBy = "user")
    private List<UserPin> userPinList = new ArrayList<>();
}
