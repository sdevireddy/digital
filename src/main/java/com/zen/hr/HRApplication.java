package com.zen.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.zen.hr.entity")
@EnableJpaRepositories(basePackages = "com.zen.hr.repository")
public class HRApplication {

	public static void main(String[] args) {
		SpringApplication.run(HRApplication.class, args);
	}

}
