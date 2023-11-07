package com.gabriel.Customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.gabriel.Backend.*", "com.gabriel.Customer.*"})
@EnableJpaRepositories(value = "com.gabriel.Backend.repository")
@EntityScan(value = "com.gabriel.Backend.model")
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

}
