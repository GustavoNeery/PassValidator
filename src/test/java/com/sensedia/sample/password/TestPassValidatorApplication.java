package com.sensedia.sample.password;

import org.springframework.boot.SpringApplication;

public class TestPassValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.from(PassValidatorApplication::main).with(TestContainersConfiguration.class).run(args);
	}

}
