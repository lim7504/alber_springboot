package org.themselves.alber.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.controller.user.dto.*;
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
        userJoinDto.setNickname("둘리2");
        userJoinDto.setEmail("aaa@aaa2");
        userJoinDto.setPassword("1111abcd");
        userService.joinUser(userJoinDto, UserType.ADMIN);

        assertEquals("둘리2", userService.getUserByEmail("aaa@aaa2").getNickname());
    }

    @Test
    public void testGetUserDetail() {
        User user = new User();
        user.setEmail("aaa@aaa");

        UserResponseDto userDto = userService.getUser(user.getEmail());

        assertEquals("aaa@aaa", userDto.getEmail());
        assertEquals("둘리", userDto.getNickname());
        assertNotNull(userDto.getUrl());
    }


    @Test
    public void testGetUserForMyPage() {
        User user = userService.getUserByEmail("aaa@aaa");
        UserMyPageDto userDto = userService.getUserForMyPage(user);

        assertEquals("aaa@aaa", userDto.getEmail());
        assertEquals("둘리", userDto.getNickname());
        assertEquals("평범한시민", userDto.getGrade());
        assertNotNull(userDto.getUrl());

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
        userService.updateNickname(1L, userNicknameDto);

        em.flush();
        em.clear();
        assertEquals("싱싱한쓰레기", userService.getUserByEmail("aaa@aaa").getNickname());
    }

    @Test
    public void testUpdatePassword() {
        String updatePwd = "1111aaaa";
        UserPasswordDto passwordDto = new UserPasswordDto();
        passwordDto.setPassword(updatePwd);

        userService.updatePassword(1L, passwordDto);
        Password password = new Password();
        em.flush();
        em.clear();
        assertTrue(password.encodeMatches(updatePwd, userService.getUser(1L).getPassword()));
    }

    @Test
    @Disabled //TODO 개발예정
    @Description("회원 탈퇴")
    public void testExitUser() {
        userService.exitUser(1L);
        em.flush();
        em.clear();
        assertThrows(CustomException.class,()->{userService.getUserByEmail("aaa@aaa");});
    }

    @Test
    public void testUpdateImage() {

        userService.updateImage(1L, 11L);
        assertEquals(11L, userService.getUser(1L).getImage().getId());
    }

}