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
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentNImageDto;
import org.themselves.alber.domain.Image;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.common.UserType;
import org.themselves.alber.repository.ImageRepository;
import org.themselves.alber.repository.UserRepository;
import org.themselves.alber.repository.WastebasketCommentRepository;
import org.themselves.alber.repository.projection.MyPageProjection;

import java.util.ArrayList;
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

    @Transactional
    public void joinUser(UserJoinDto userJoinDto, UserType userType) {
        if (userRepository.findByEmail(userJoinDto.getEmail()).isPresent())
            throw new CustomException(StatusCode.EMAIL_DUPLICATION);

        if (userRepository.findByNickname(userJoinDto.getNickname()).isPresent())
            throw new CustomException(StatusCode.NICKNAME_DUPLICATION);

        User user = modelMapper.map(userJoinDto, User.class);
        user.joinCheckNSetting(userType);

        userRepository.save(user);
    }


    public Page<User> getUserAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }


    public User getUserOne(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

        return user.get();
    }


    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

        return user.get();
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
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);
    }


    @Transactional
    public void exitUser(String email) {
        Optional<User> updateUser = userRepository.findByEmail(email);
        if (!updateUser.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

//        updateUser.get().setStatus(UserStatus.INACTIVE);
//        updateUser.get().setExitedDate(LocalDateTime.now());
    }


    public boolean existUserEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public void updateNickname(String email, UserNicknameDto userNicknameDto) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

        user.get().updateNickname(userNicknameDto.getNickname());
    }

    public void updatePassword(String email, UserPasswordDto passwordDto) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

        user.get().updatePassword(passwordDto.getPassword());
    }

    public void updateImage(String email, Long image_id) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

        Optional<Image> image = imageRepository.findById(image_id);

        user.get().updateImage(image.get());
    }

    public UserMyPageDto getUserForMyPage(User user) {
        User findUser = userRepository.findByUserAndImageUrlAndPinList(user.getId());
        UserMyPageDto userDto = modelMapper.map(findUser, UserMyPageDto.class);
        userDto.setPinCnt(findUser.getUserPinList().size());
        if(findUser.getImage() != null)
            userDto.setUrl(findUser.getImage().getUrl());

        List<WastebasketCommentNImageDto> commentList = wastebasketCommentRepository.findByUserComment(user);
        for (WastebasketCommentNImageDto commentDto : commentList) {
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
