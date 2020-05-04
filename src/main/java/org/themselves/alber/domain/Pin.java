package org.themselves.alber.domain;

import lombok.Getter;
import lombok.Setter;
import org.themselves.alber.domain.common.BaseCreatedEntity;

import javax.persistence.*;

@Entity
@IdClass(PinId.class)
@Getter @Setter
public class Pin extends BaseCreatedEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "box_id")
    private Wastebasket wastebasket;

    public static Pin createPin(User user, Wastebasket wastebasket) {
        Pin pin = new Pin();
        pin.setUser(user);
        pin.setWastebasket(wastebasket);
        return pin;
    }
}
