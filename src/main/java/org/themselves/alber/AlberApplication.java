package org.themselves.alber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AlberApplication {

	private static final String PROPERTIES = "spring.config.location=classpath:/application.yml,classpath:/db.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(AlberApplication.class)
				.properties(PROPERTIES)
				.run(args);
	}

}
