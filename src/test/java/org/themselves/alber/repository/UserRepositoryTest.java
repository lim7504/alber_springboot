package org.themselves.alber.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.domain.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Description("JPQL")
    public void testFindByEmail() {
        User user = new User();
        user.setEmail("eee@eee");
        User byEmail = userRepository.findByUserJPQL(user.getEmail());
        System.out.println(byEmail.getImage().getUrl());
        //assertEquals("둘리", byEmail.get().getNickname());
    }


    @Test
    @Description("NativeQuery")
    public void testFindByNativeQuery() {
        User user = new User();
        user.setEmail("eee@eee");
        Object object = userRepository.findByNativeQuery(user.getEmail());
        Object[] strArray = (Object[])object;
        System.out.println(strArray[0]);
        //assertEquals("둘리", byEmail.get().getNickname());
    }

    @Test
    @Description("NativeQuery2")
    public void testFindByNativeQuery2() {
        User user = new User();
        user.setEmail("eee@eee");
        UserProjection userProjectionList = userRepository.findByNativeQuery2(user.getEmail());
        System.out.println(userProjectionList.getNickname());
        System.out.println(userProjectionList.getUrl());
        //assertEquals("둘리", byEmail.get().getNickname());
    }

    @Test
    @Description("NativeQuery3")
    public void testFindByNativeQuery3() {
        User user = new User();
        user.setEmail("eee@eee");
        List<UserProjection> userProjectionList = userRepository.findByNativeQuery3();
        for (UserProjection item : userProjectionList) {
            System.out.println(item.getNickname());
            System.out.println(item.getUrl());
        }

        //assertEquals("둘리", byEmail.get().getNickname());
    }
}