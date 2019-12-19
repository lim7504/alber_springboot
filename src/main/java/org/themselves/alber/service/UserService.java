package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Boolean JoinUser(User user){

        try {
            user.setJoinedDate(LocalDateTime.now());
            user.setLastLoginDate(LocalDateTime.now());
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

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

//    @Transactional
//    public Boolean login(User user) {
//
//        return !deletedUser.isPresent();
//    }

}
