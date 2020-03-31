package org.themselves.alber.repository;

import org.springframework.data.repository.query.Param;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.WastebasketComment;
import org.themselves.alber.repository.projection.MyPageProjection;

import java.util.List;

public interface WastebasketCommentRepositoryCustom {
    List<WastebasketComment> findByUserQueryDSL(User user);
}
