package org.themselves.alber.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.domain.QUser;
import org.themselves.alber.domain.User;
import org.themselves.alber.repository.projection.UserProjection;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    EntityManager em;


    @Test
    @Description("JPQL")
    public void testFindByEmail() {
        User user = new User();
        user.setEmail("eee@eee");
        User findUser = userRepository.findByUserAndImageUrl(user.getEmail());
        if(findUser.getImage() != null)
            System.out.println(findUser.getImage().getUrl());
        System.out.println(findUser.getEmail());
        System.out.println(findUser.getNickname());
        System.out.println(findUser.getGrade());

    }

    @Test
    @Description("JPQL")
    public void testFindByEmail2() {
        User user = new User();
        user.setEmail("aaa@aaa");
        User findUser = userRepository.findByUserJPQL2(user.getEmail());
        if(findUser.getImage() != null)
            System.out.println(findUser.getImage().getUrl());
        System.out.println(findUser.getEmail());
        System.out.println(findUser.getNickname());
        System.out.println(findUser.getGrade());
        System.out.println(findUser.getUserPinList().size());

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

    @Test
    @Description("QueryDSL")
    public void testQueryDSL() {

        JPAQueryFactory query = new JPAQueryFactory(em);
        QUser qUser = new QUser("u");

        User result = query
                .selectFrom(qUser)
                .where(qUser.email.eq("aaa@aaa"))
                .fetchOne();

        assertThat(result.getNickname()).isEqualTo("둘리");
    }

    @Test
    @Description("마이페이지-프로필이미지,이메일,닉네임,등급")
    public void testMyPagePart1() {
        User user = new User();
        user.setEmail("eee@eee");
        User findUser = userRepository.findByUserAndImageUrl(user.getEmail());
        System.out.println(findUser.getImage().getUrl());

        //assertEquals("둘리", byEmail.get().getNickname());

    }
}