package com.sensedia.sample.password;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class PassValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassValidatorApplication.class, args);
	}

}
