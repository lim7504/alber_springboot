package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.UserSocialType;
import org.themselves.alber.domain.UserStatus;
import org.themselves.alber.domain.UserType;
import org.themselves.alber.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void JoinUser(User user) {

            if (userRepository.findByEmail(user.getEmail()).isPresent())
                throw new CustomException(StatusCode.EMAIL_DUPLICATION);

            if (userRepository.findByNickname(user.getNickname()).isPresent())
                throw new CustomException(StatusCode.NICKNAME_DUPLICATION);

            user.setSocialType(UserSocialType.None);
            user.setLastLoginDate(LocalDateTime.now());
            user.setStatus(UserStatus.ACTIVE);
            user.setType(UserType.USER);
            user.encodePassword(passwordEncoder);

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

    public User getUserByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

        return user.get();
    }

    @Transactional
    public User updateUser(User user) {

        Optional<User> updateUser = userRepository.findByEmail(user.getEmail());
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

    @Transactional
    public User exitUser(String email) {

        Optional<User> updateUser = userRepository.findByEmail(email);
        if (!updateUser.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

        updateUser.get().setStatus(UserStatus.INACTIVE);
        updateUser.get().setExitedDate(LocalDateTime.now());

        return updateUser.get();
    }

}
