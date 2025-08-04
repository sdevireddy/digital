package com.zen.digital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

public class DigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalApplication.class, args);
	}

}
