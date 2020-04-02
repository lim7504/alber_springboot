package org.themselves.alber.repository;

import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentNImageDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.Wastebasket;
import org.themselves.alber.domain.WastebasketComment;
import org.themselves.alber.domain.WastebasketImage;

import java.util.List;

public interface WastebasketCommentRepositoryCustom {
    List<WastebasketComment> findByUserQueryDSL(User user);
    List<WastebasketCommentNImageDto> findByUserComment(User user);

    List<WastebasketImage> findByUserWastebasketImage(List<Wastebasket> wastebaskets);
}
