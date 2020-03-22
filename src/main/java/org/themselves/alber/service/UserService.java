package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.user.dto.UserJoinDto;
import org.themselves.alber.controller.user.dto.UserUpdateDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.common.UserSocialType;
import org.themselves.alber.domain.common.UserStatus;
import org.themselves.alber.domain.common.UserType;
import org.themselves.alber.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void joinUser(UserJoinDto userJoinDto, UserType userType) {
        if (userRepository.findByEmail(userJoinDto.getEmail()).isPresent())
            throw new CustomException(StatusCode.EMAIL_DUPLICATION);

        if (userRepository.findByNickname(userJoinDto.getNickname()).isPresent())
            throw new CustomException(StatusCode.NICKNAME_DUPLICATION);

        User user = modelMapper.map(userJoinDto, User.class);
        user.joinUser(userType);

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


    @Transactional
    public User updateUser(UserUpdateDto userUpdateDto, String userEmail) {
        Optional<User> updateUser = userRepository.findByEmail(userEmail);
        if (!updateUser.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

        updateUser.get().updateUser(userUpdateDto);
        return updateUser.get();
    }


    @Transactional
    public User updateUserAdmin(UserUpdateDto userUpdateDto, Long id) {
        Optional<User> updateUser = userRepository.findById(id);
        if (!updateUser.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

        updateUser.get().updateUser(userUpdateDto);
        return updateUser.get();
    }


    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            throw new CustomException(StatusCode.DELETE_FAIL);
    }


    @Transactional
    public User exitUser(String email) {
        Optional<User> updateUser = userRepository.findByEmail(email);
        if (!updateUser.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

        updateUser.get().setStatus(UserStatus.INACTIVE);
        updateUser.get().setExitedDate(LocalDateTime.now());

        return updateUser.get();
    }


    public boolean existUserEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }
}
