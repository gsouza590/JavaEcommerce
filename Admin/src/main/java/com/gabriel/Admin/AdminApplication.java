package com.gabriel.Admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.gabriel.Backend.*", "com.gabriel.Admin.*"})
@EnableJpaRepositories(value = "com.gabriel.Backend.repository")
@EntityScan(value = "com.gabriel.Backend.model")
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
