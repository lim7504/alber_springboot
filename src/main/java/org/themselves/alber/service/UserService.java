package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.user.dto.*;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistCommentDto;
import org.themselves.alber.domain.Image;
import org.themselves.alber.domain.Notifycation;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.common.UserStatus;
import org.themselves.alber.domain.common.UserType;
import org.themselves.alber.repository.ImageRepository;
import org.themselves.alber.repository.NotifycationRepository;
import org.themselves.alber.repository.UserRepository;
import org.themselves.alber.repository.WastebasketCommentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    private final WastebasketCommentRepository wastebasketCommentRepository;
    private final NotifycationRepository notifycationRepository;

    @Transactional
    public void joinUser(UserJoinDto userJoinDto, UserType userType) {

        if (userRepository.findByEmail(userJoinDto.getEmail()).isPresent())
            throw new CustomException(StatusCode.EMAIL_DUPLICATION);

        if (userRepository.findByNickname(userJoinDto.getNickname()).isPresent())
            throw new CustomException(StatusCode.NICKNAME_DUPLICATION);

        User user = User.createUser(userJoinDto, userType);
        userRepository.save(user);
    }


    public Page<User> getUserAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new CustomException(StatusCode.ACCOUNT_NOT_FOUND));
    }


    public User getUser(Long Id) {
        User user = userRepository.findById(Id)
                .orElseThrow(()->new CustomException(StatusCode.ACCOUNT_NOT_FOUND));

        return user;
    }


    public boolean existUserNickname(String nickname) {
        Optional<User> user = userRepository.findByNickname(nickname);
        return user.isPresent();
    }


//    @Transactional
//    public User updateUser(UserUpdateDto userUpdateDto, String userEmail) {
//        Optional<User> updateUser = userRepository.findByEmail(userEmail);
//        if (!updateUser.isPresent())
//            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);
//
//        updateUser.get().updateCheckNSetting(userUpdateDto);
//        return updateUser.get();
//    }


//    @Transactional
//    public User updateUserAdmin(UserUpdateDto userUpdateDto, Long id) {
//        Optional<User> updateUser = userRepository.findById(id);
//        if (!updateUser.isPresent())
//            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);
//
//        updateUser.get().updateCheckNSetting(userUpdateDto);
//        return updateUser.get();
//    }


    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        userRepository.findById(id)
                .orElseThrow(()->new CustomException(StatusCode.DELETE_FAIL));
    }


    @Transactional
    public void exitUser(Long userId) {
        User updateUser = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(StatusCode.ACCOUNT_NOT_FOUND));

        updateUser.setStatus(UserStatus.INACTIVE);
        updateUser.setExitedDate(LocalDateTime.now());
    }


    public boolean existUserEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }


    public void updateNickname(Long userId, UserNicknameDto userNicknameDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(StatusCode.ACCOUNT_NOT_FOUND));

        user.updateNickname(userNicknameDto.getNickname());
    }


    public void updatePassword(Long userId, UserPasswordDto passwordDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(StatusCode.ACCOUNT_NOT_FOUND));

        user.updatePassword(passwordDto.getPassword());
    }


    public void updateImage(Long userId, Long image_id) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(StatusCode.ACCOUNT_NOT_FOUND));

        Image image = imageRepository.findById(image_id)
                .orElseThrow(()-> new CustomException(StatusCode.IMAGE_NOT_FOUND));

        user.updateImage(image);
    }


    public UserMyPageDto getUserForMyPage(Long userId) {
        User user = userRepository.findByUserAndImageUrlAndPinList(userId);
        UserMyPageDto userDto = modelMapper.map(user, UserMyPageDto.class);
        userDto.setPinCnt(user.getUserPinList().size());
        if(user.getImage() != null)
            userDto.setUrl(user.getImage().getUrl());

        List<WastebasketCommentForMyRegistCommentDto> commentList = wastebasketCommentRepository.findByUserForMyRegistComment(user);
        for (WastebasketCommentForMyRegistCommentDto commentDto : commentList) {
            userDto.getComment().add(commentDto);
        }

//        List<MyPageProjection> myPageProjectionList = wastebasketCommentRepository.findByUser(user.getId());
//        for (MyPageProjection myPageProjection : myPageProjectionList) {
//            WastebasketCommentNImageDto commentNImageDto = new WastebasketCommentNImageDto();
//            commentNImageDto.setContents(myPageProjection.getContents().toString());
//            commentNImageDto.setBoxName(myPageProjection.getBox_Name().toString());
//            commentNImageDto.setAreaSi(myPageProjection.getArea_Si().toString());
//            commentNImageDto.setImage(myPageProjection.getUrl().toString());
//            userDto.getComment().add(commentNImageDto);
//        }


        return userDto;
    }

    public UserResponseDto getUser(String email) {
        User findUser = userRepository.findByUserAndImageUrl(email);
        UserResponseDto userDto = modelMapper.map(findUser, UserResponseDto.class);
        if(findUser.getImage() != null)
            userDto.setUrl(findUser.getImage().getUrl());
        return userDto;
    }
}
