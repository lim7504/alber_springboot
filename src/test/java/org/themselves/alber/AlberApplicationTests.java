package org.themselves.alber;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
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

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.config.location=classpath:/application.yml,classpath:/db.yml")
@Transactional
@Rollback(false)
class AlberApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Test
	void testUser() {

		User user = new User();
		user.setNickname("둘리");
		user.setEmail("abcd@abcd");
		user.setPassword("1234");
		userService.saveUser(user);

		User findUser = userRepository.findOne(user.getId());
		Assert.assertThat(findUser.getId(), CoreMatchers.is(user.getId()));

	}

}
