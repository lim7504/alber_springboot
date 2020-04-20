package org.themselves.alber;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@SpringBootApplication
public class AlberApplication {

	private static final String PROPERTIES = "spring.config.location=classpath:/application.yml,classpath:/db.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(AlberApplication.class)
				.properties(PROPERTIES)
				.run(args);
	}

//	@Bean //로그인 했을시 등록
//	public AuditorAware<String> auditorProvider() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (null == authentication || !authentication.isAuthenticated()) {
//			return () -> Optional.of("로컬서버");
//		}
//		Object principal = authentication.getPrincipal();
//
////		return () -> Optional.of("로그인유저");
//	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

}
