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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        Optional<User> user = userRepository.findByEmail("aaa@aaa");
        User findUser = userRepository.findByUserAndImageUrlAndPinList(user.get().getId());
        if(findUser.getImage() != null) {
            System.out.println(findUser.getImage().getUrl());
            assertNotNull(findUser.getImage().getUrl());
        }
        assertEquals("둘리", findUser.getNickname());
    }


    @Test
    @Description("NativeQuery")
    public void testFindByNativeQuery() {
        User user = new User();
        user.setEmail("aaa@aaa");
        Object object = userRepository.findByNativeQuery(user.getEmail());
        Object[] strArray = (Object[])object;
        System.out.println(strArray[0]);
        assertEquals("둘리", strArray[0]);
    }

    @Test
    @Description("NativeQuery2")
    public void testFindByNativeQuery2() {
        User user = new User();
        user.setEmail("aaa@aaa");
        UserProjection userProjection = userRepository.findByNativeQuery2(user.getEmail());
        System.out.println(userProjection.getNickname());
        System.out.println(userProjection.getUrl());
        assertEquals("둘리", userProjection.getNickname());
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
}