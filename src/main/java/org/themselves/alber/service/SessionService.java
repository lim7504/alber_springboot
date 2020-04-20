package org.themselves.alber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.JwtTokenProvider;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.domain.Password;
import org.themselves.alber.domain.User;
import org.themselves.alber.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SessionService implements UserDetailsService {

    private final UserRepository userRepository;

    public User login(User user) {
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());
        if (!findUser.isPresent())
            throw new CustomException(StatusCode.ACCOUNT_NOT_FOUND);

        Password pwd = new Password();
        if(!pwd.encodeMatches(user.getPassword(), findUser.get().getPassword()))
            throw new CustomException(StatusCode.PASSWORD_ALONG);

        return findUser.get();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getId().toString())
                .password(user.get().getPassword())
                .roles(user.get().getType().name())
                .build();
    }
}
