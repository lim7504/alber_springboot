package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
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
    public void JoinUser(User user) {

            if (userRepository.findByEmail(user.getEmail()).isPresent())
                throw new CustomException(StatusCode.EMAIL_DUPLICATION);

            if (userRepository.findByNickname(user.getNickname()).isPresent())
                throw new CustomException(StatusCode.NICKNAME_DUPLICATION);

            user.setJoinedDate(LocalDateTime.now());
            user.setLastLoginDate(LocalDateTime.now());
            user.setStatus(UserStatus.ACTIVE);


            //TODO: password encrypt code
            userRepository.save(user);

    }

    public List<User> getUserAll(){
        return userRepository.findAll();
    }

    public User getUserOne(Long id) {

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

        return user.get();
    }

    @Transactional
    public User updateUser(User user) {

        Optional<User> updateUser = userRepository.findById(user.getId());
        if (!updateUser.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

        updateUser.get().setNickname(user.getNickname());
        updateUser.get().setEmail(user.getEmail());
        updateUser.get().setPassword(user.getPassword());

        return updateUser.get();
    }

    @Transactional
    public void deleteUser(Long id) {

        userRepository.deleteById(id);

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            throw new CustomException(StatusCode.DELETE_FAIL);
    }

    public void login(User user) {

        Optional<User> findUser = userRepository.findByEmail(user.getEmail());

        if (!findUser.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

        String encryptPassword = user.getPassword();  //TODO: password encrypt code
        if (!encryptPassword.equals(findUser.get().getPassword()))
            throw new CustomException(StatusCode.PASSWORD_ALONG);

        //TODO :authorized
    }

    public void logout() {

        //TODO :unauthorized

    }
}
