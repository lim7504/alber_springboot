package org.themselves.alber.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    public void testEncodingPassword() {
        Password password = new Password();
        String oriPwd = "1234";
        String encodePwd = password.encodePassword(oriPwd);
        assertNotEquals(oriPwd, encodePwd);
        System.out.println(encodePwd);
    }

    @Test
    public void testCheckPasswordPolicy() {
        Password password = new Password();
        String pwd = "";
        assertFalse(password.CheckPasswordPolicy(pwd),"empty password");

        pwd = "1111";
        assertFalse(password.CheckPasswordPolicy(pwd),"password length lessthan 8");

        pwd = "1111111";
        assertFalse(password.CheckPasswordPolicy(pwd),"only Number");

        pwd = "aaaaaaaa";
        assertFalse(password.CheckPasswordPolicy(pwd),"only String");

        pwd = "1234abcd";
        assertTrue(password.CheckPasswordPolicy(pwd),"success!!");
    }
}