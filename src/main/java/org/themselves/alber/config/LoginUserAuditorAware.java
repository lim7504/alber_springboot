package org.themselves.alber.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.themselves.alber.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginUserAuditorAware  implements AuditorAware<String> {

    private final UserRepository userRepository;

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication || !authentication.isAuthenticated()) {
            return Optional.of("로컬서버");
        }

        User user = (User) authentication.getPrincipal();
        return Optional.of(user.getUsername());
    }
}