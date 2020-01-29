package org.themselves.alber.domain;

import java.io.Serializable;
import java.util.Objects;

public class PinWastebasketId implements Serializable {

    private Long user;

    private Long wastebasket;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PinWastebasketId userPinId = (PinWastebasketId) o;
        return Objects.equals(user, userPinId.user) &&
                Objects.equals(wastebasket, userPinId.wastebasket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, wastebasket);
    }
}