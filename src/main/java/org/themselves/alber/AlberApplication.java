package org.themselves.alber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class AlberApplication {

	private static final String PROPERTIES = "spring.config.location=classpath:/application.yml,classpath:/db.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(AlberApplication.class)
				.properties(PROPERTIES)
				.run(args);
	}

	@Bean //로그인 했을시 등록
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of("로그인유저");
	}


}
