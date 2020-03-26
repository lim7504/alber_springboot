package org.themselves.alber.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.controller.user.dto.UserJoinDto;
import org.themselves.alber.controller.user.dto.UserNicknameDto;
import org.themselves.alber.controller.user.dto.UserPasswordDto;
import org.themselves.alber.domain.Image;
import org.themselves.alber.domain.Password;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.common.UserType;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    EntityManager em;

    @Test
    public void joinUser() {
        UserJoinDto userJoinDto = new UserJoinDto();
        userJoinDto.setNickname("마빡이");
        userJoinDto.setEmail("rrr@rrr");
        userJoinDto.setPassword("1111aaaa");
        userService.joinUser(userJoinDto, UserType.ADMIN);

        assertEquals("마빡이", userService.getUserByEmail("rrr@rrr").getNickname());
    }

    @Test
    public void testExistUserNickname() {
        assertTrue(userService.existUserNickname("둘리"));
        assertFalse(userService.existUserNickname("하나"));
    }

    @Test
    public void testExistUserEmail() {
        assertTrue(userService.existUserEmail("aaa@aaa"));
        assertFalse(userService.existUserEmail("aaa@fff"));
    }

    @Test
    public void testUpdateNickname() {
        UserNicknameDto userNicknameDto = new UserNicknameDto();
        userNicknameDto.setNickname("싱싱한쓰레기");
        userService.updateNickname("aaa@aaa", userNicknameDto);

        em.flush();
        em.clear();
        assertEquals("싱싱한쓰레기", userService.getUserByEmail("aaa@aaa").getNickname());
    }

    @Test
    public void testUpdatePassword() {
        String email = "aaa@aaa";
        String updatePwd = "1111aaaa";
        UserPasswordDto passwordDto = new UserPasswordDto();
        passwordDto.setPassword(updatePwd);

        userService.updatePassword(email, passwordDto);
        Password password = new Password();
        em.flush();
        em.clear();
        assertTrue(password.encodeMatches(updatePwd, userService.getUserByEmail(email).getPassword()));
    }

    @Test
    @Disabled //TODO 개발예정
    @Description("회원 탈퇴")
    public void testExitUser() {
        userService.exitUser("aaa@aaa2");
        em.flush();
        em.clear();
        assertThrows(CustomException.class,()->{userService.getUserByEmail("aaa@aaa2");});
    }

    @Test
    public void testUpdateImage() {
        String email = "eee@eee";
        userService.updateImage(email, 11L);

        assertEquals(11L, userService.getUserByEmail(email).getImage().getId());
    }

}