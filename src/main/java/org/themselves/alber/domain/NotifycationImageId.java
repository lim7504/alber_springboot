package org.themselves.alber.domain;

import java.io.Serializable;
import java.util.Objects;

public class NotifycationImageId implements Serializable {

    private Long image;

    private Long notifycation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotifycationImageId ncId = (NotifycationImageId) o;
        return Objects.equals(image, ncId.image) &&
                Objects.equals(notifycation, ncId.notifycation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, notifycation);
    }
}
