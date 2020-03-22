package org.themselves.alber.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
public class Password {

    private PasswordEncoder passwordEncoder;

    public Password() {
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public String encodePassword(String oriPassword) {
        return passwordEncoder.encode(oriPassword);
    }

    public Boolean CheckPasswordPolicy(String pwd) {
        return !pwd.isEmpty()
                && this.isPasswordMoreThanEqualEight(pwd)
                && this.isPasswordContainsNumber(pwd)
                && this.isPasswordContainsString(pwd);
    }

    private Boolean isPasswordMoreThanEqualEight(String pwd) {
        return pwd.length() >= 8;
    }

    private Boolean isPasswordContainsNumber(String pwd) {
        return pwd.matches(".*[0-9].*");
    }

    private Boolean isPasswordContainsString(String pwd) {
        return pwd.matches(".*[a-zA-Z].*");
    }

    public boolean encodeMatches(String expected, String target) {
        return passwordEncoder.matches(expected, target);
    }

}
