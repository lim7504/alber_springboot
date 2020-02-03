package org.themselves.alber.domain;

import java.io.Serializable;
import java.util.Objects;

public class WastebasketCommentId implements Serializable {

    private Long id;

    private Long user;

    private Long wastebasket;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WastebasketCommentId commentId = (WastebasketCommentId) o;
        return Objects.equals(id, commentId.id) &&
                Objects.equals(user, commentId.user) &&
                Objects.equals(wastebasket, commentId.wastebasket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, wastebasket);
    }
}
