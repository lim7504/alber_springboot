package org.themselves.alber.domain;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.domain.common.UserType;

import java.util.IllformedLocaleException;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test()
    public void testCheckPasswordPolicy() {
        User user = new User();
        user.setPassword("1111");
        assertThrows(CustomException.class,() -> { user.joinCheckNSetting(UserType.USER);});
        user.setPassword("1111aaaa");
        user.joinCheckNSetting(UserType.USER);
    }

    @Test
    public void testUpdateNickName() {
        String nickname = "싱싱한쓰레기";
        User user = new User();
        user.updateNickname(nickname);

        assertEquals("싱싱한쓰레기", user.getNickname());
    }


    @Test
    public void testUpdateImage() {
        Image image = new Image();
        image.setId(11L);
        User user = new User();
        user.updateImage(image);

        assertEquals(image.getId(), user.getImage().getId());
    }


}