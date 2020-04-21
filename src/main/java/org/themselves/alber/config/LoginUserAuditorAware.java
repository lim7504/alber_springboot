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
        String auditor;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (null == authentication || !authentication.isAuthenticated())
            auditor = "local test";
        else if(authentication.getPrincipal() instanceof User)
            auditor = ((User) authentication.getPrincipal()).getUsername();
        else
            auditor = "join";

        return Optional.of(auditor);
    }
}