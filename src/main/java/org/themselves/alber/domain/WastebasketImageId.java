package org.themselves.alber.domain;

import java.io.Serializable;
import java.util.Objects;

public class WastebasketImageId implements Serializable {

    private Long image;

    private Long wastebasket;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WastebasketImageId wiId = (WastebasketImageId) o;
        return Objects.equals(image, wiId.image) &&
                Objects.equals(wastebasket, wiId.wastebasket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, wastebasket);
    }
}
