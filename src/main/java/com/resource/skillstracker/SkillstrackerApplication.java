package com.resource.skillstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = "com.resource.skillstracker.repository")
@ComponentScan(basePackages = "com.resource.skillstracker")
public class SkillstrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillstrackerApplication.class, args);
	}

}
