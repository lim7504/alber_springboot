package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentNImageDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.Wastebasket;
import org.themselves.alber.domain.WastebasketComment;
import org.themselves.alber.repository.*;
import org.themselves.alber.repository.projection.MyPageProjection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WastebasketCommentService {

    private final WastebasketCommentRepository wastebasketCommentRepository;

    @Transactional
    public void addWastebasketComment(Wastebasket wastebasket, User user, String contents) {
        WastebasketComment wastebasketComment = new WastebasketComment();
        wastebasketComment.setWastebasket(wastebasket);
        wastebasketComment.setUser(user);
        wastebasketComment.setContents(contents);

        wastebasketCommentRepository.save(wastebasketComment);
    }

    @Transactional
    public void setWastebasketComment(User user, Long comment_id, String contents) {
        Optional<WastebasketComment> wastebasketComment = wastebasketCommentRepository.findById(comment_id);

        if(!wastebasketComment.isPresent())
            throw new CustomException(StatusCode.WASTEBASKETCOMMENT_NOT_FOUND);

        if(!wastebasketComment.get().getUser().getId().equals(user.getId()))
            throw new CustomException(StatusCode.WASTEBASKETCOMMENT_NOT_SAME_USER);

        wastebasketComment.get().setContents(contents);
    }

    @Transactional
    public void delWastebasketComment(User user, Long comment_id) {

        Optional<WastebasketComment> wastebasketComment = wastebasketCommentRepository.findById(comment_id);

        if(!wastebasketComment.isPresent())
            throw new CustomException(StatusCode.WASTEBASKETCOMMENT_NOT_FOUND);

        if(!wastebasketComment.get().getUser().getId().equals(user.getId()))
            throw new CustomException(StatusCode.WASTEBASKETCOMMENT_NOT_SAME_USER);

        wastebasketCommentRepository.delete(wastebasketComment.get());
    }

    public List<WastebasketComment> getWastebasketCommentList(Wastebasket wastebasket) {
        List<WastebasketComment> wastebasketCommentList = wastebasketCommentRepository.findByWastebasketId(wastebasket);
        return wastebasketCommentList;
    }

    public List<WastebasketCommentNImageDto> getWastebasketCommentListByUser(User user) {
        List<WastebasketCommentNImageDto> commentNImageDtoList = new ArrayList<>();
        List<MyPageProjection> myPageProjectionList = wastebasketCommentRepository.findByUser(user.getId());

        for (MyPageProjection myPageProjection : myPageProjectionList) {
            WastebasketCommentNImageDto commentNImageDto = new WastebasketCommentNImageDto();
            commentNImageDto.setContents(myPageProjection.getContents().toString());
            commentNImageDto.setBoxName(myPageProjection.getBox_Name().toString());
            commentNImageDto.setAreaSi(myPageProjection.getArea_Si().toString());
            commentNImageDto.setImage(myPageProjection.getUrl().toString());
            commentNImageDtoList.add(commentNImageDto);
        }
        return commentNImageDtoList;
    }
}

