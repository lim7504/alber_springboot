package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.exception.CustomException;
import org.themselves.alber.config.exception.ErrorCode;
import org.themselves.alber.controller.user.UserLoginDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.UserStatus;
import org.themselves.alber.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Boolean JoinUser(User user) throws CustomException{

        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new CustomException(ErrorCode.EMAIL_DUPLICATION);

        if(userRepository.findByNickname(user.getNickname()).isPresent())
            throw new CustomException(ErrorCode.NICKNAME_DUPLICATION);

        user.setJoinedDate(LocalDateTime.now());
        user.setLastLoginDate(LocalDateTime.now());
        user.setStatus(UserStatus.ACTIVE);

        //TODO: password encrypt code

        userRepository.save(user);

        return true;
    }

    public List<User> getUserAll(){
        return userRepository.findAll();
    }

    public Optional<User> getUserOne(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User updateUser(User user) {
        User updateUser = userRepository.findById(user.getId()).get();
        updateUser.setNickname(user.getNickname());
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(user.getPassword());

        return updateUser;
    }

    @Transactional
    public Boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        Optional<User> deletedUser = userRepository.findById(id);
        return !deletedUser.isPresent();
    }

    public Boolean login(User user) {

        Optional<User> findUser = userRepository.findByEmail(user.getEmail());

        if (!findUser.isPresent())
            throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);

        String encryptPassword = user.getPassword();  //TODO: password encrypt code
        if (!encryptPassword.equals(findUser.get().getPassword()))
            throw new CustomException(ErrorCode.PASSWORD_ALONG);

        //TODO :authorized

        return true;
    }

    public Boolean logout() {

        //TODO :unauthorized

        return true;
    }
}
