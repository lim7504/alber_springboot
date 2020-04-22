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
public class LoginUserAuditorAware  implements AuditorAware<Long> {

    private final UserRepository userRepository;

    @Override
    public Optional<Long> getCurrentAuditor() {
        Long auditor;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (null == authentication || !authentication.isAuthenticated())
            auditor = -1L;
        else if(authentication.getPrincipal() instanceof User)
            auditor = Long.parseLong(((User) authentication.getPrincipal()).getUsername());
        else
            auditor = 0L;

        return Optional.of(auditor);
    }
}