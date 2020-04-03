package org.themselves.alber.repository;

import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistWastebasketDto;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistCommentDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.Wastebasket;
import org.themselves.alber.domain.WastebasketComment;
import org.themselves.alber.domain.WastebasketImage;

import java.util.List;
import java.util.Set;

public interface WastebasketCommentRepositoryCustom {
    List<WastebasketComment> findByUserQueryDSL(User user);
    List<WastebasketCommentForMyRegistCommentDto> findByUserForMyRegistComment(User user);
    List<WastebasketCommentForMyRegistWastebasketDto> findByUserForMyRegistWastebasket(Set<Long> wastebasketList);
    List<WastebasketImage> findByUserWastebasketImage(List<Wastebasket> wastebaskets);
}
