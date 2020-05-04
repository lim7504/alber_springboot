package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.wastebasket.dto.WastebasketForMyRegistWastebasketDto;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistCommentDto;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistWastebasketDto;
import org.themselves.alber.domain.Pin;
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
    private final UserRepository userRepository;
    private final WastebasketRepository wastebasketRepository;

    @Transactional
    public void addWastebasketComment(Long wastebasketId, Long userId, String contents) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(StatusCode.ACCOUNT_NOT_FOUND));

        Wastebasket wastebasket = wastebasketRepository.findById(wastebasketId)
                .orElseThrow(() -> new CustomException(StatusCode.WASTEBASKET_NOT_FOUND));

        WastebasketComment wastebasketComment = WastebasketComment.createWastebasketComment(user, wastebasket, contents);
        wastebasketCommentRepository.save(wastebasketComment);
    }

    @Transactional
    public void setWastebasketComment(Long userId, Long comment_id, String contents) {
        WastebasketComment wastebasketComment = wastebasketCommentRepository.findById(comment_id)
                .orElseThrow(()->new CustomException(StatusCode.WASTEBASKETCOMMENT_NOT_FOUND));

        if(!wastebasketComment.getUser().getId().equals(userId))
            throw new CustomException(StatusCode.WASTEBASKETCOMMENT_NOT_SAME_USER);

        wastebasketComment.updateContents(contents);
    }

    @Transactional
    public void delWastebasketComment(Long userId, Long comment_id) {
        WastebasketComment wastebasketComment = wastebasketCommentRepository.findById(comment_id)
                .orElseThrow(()-> new CustomException(StatusCode.WASTEBASKETCOMMENT_NOT_FOUND));

        if(!wastebasketComment.getUser().getId().equals(userId))
            throw new CustomException(StatusCode.WASTEBASKETCOMMENT_NOT_SAME_USER);

        wastebasketCommentRepository.delete(wastebasketComment);
    }

    public List<WastebasketComment> getWastebasketCommentList(Wastebasket wastebasket) {
        List<WastebasketComment> wastebasketCommentList = wastebasketCommentRepository.findByWastebasketId(wastebasket);
        return wastebasketCommentList;
    }

//    public List<WastebasketCommentForMyRegistCommentDto> getWastebasketCommentListByUser(User user) {
//        List<WastebasketCommentForMyRegistCommentDto> commentNImageDtoList = new ArrayList<>();
//        List<MyPageProjection> myPageProjectionList = wastebasketCommentRepository.findByUser(user.getId());
//
//        for (MyPageProjection myPageProjection : myPageProjectionList) {
//            WastebasketCommentForMyRegistCommentDto commentNImageDto = new WastebasketCommentForMyRegistCommentDto();
//            commentNImageDto.setContents(myPageProjection.getContents().toString());
//            commentNImageDto.setBoxName(myPageProjection.getBox_Name().toString());
//            commentNImageDto.setAreaSi(myPageProjection.getArea_Si().toString());
//            commentNImageDto.setImage(myPageProjection.getUrl().toString());
//            commentNImageDtoList.add(commentNImageDto);
//        }
//        return commentNImageDtoList;
//    }


}

