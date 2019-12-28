package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.domain.User;
import org.themselves.alber.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SessionService {

    private final UserRepository userRepository;

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
