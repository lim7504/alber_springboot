package org.themselves.alber.domain;

import lombok.Getter;
import lombok.Setter;
import org.themselves.alber.domain.common.BaseCreatedEntity;

import javax.persistence.*;

@Entity
@IdClass(UserPinId.class)
@Getter @Setter
public class UserPin extends BaseCreatedEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Wastebasket wastebasket;
}
