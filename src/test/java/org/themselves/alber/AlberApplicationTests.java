package org.themselves.alber;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.domain.User;
import org.themselves.alber.repository.UserRepository;
import org.themselves.alber.service.UserService;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.config.location=classpath:/application.yml,classpath:/db.yml")
@Transactional
@Rollback(false)
class AlberApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	EntityManager em;


//	@Test
//	void testUser() throws Exception {
//
//		User user = new User();
//		user.setNickname("둘리");
//		user.setEmail("abcd@abcd");
//		user.setPassword("1234");
//		userService.joinUser(user);
//
//
//
//	}

}
