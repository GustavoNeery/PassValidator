package com.sensedia.sample.password.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface IPasswordApi {

	@GetMapping(value = "/password-validations", produces = { "application/json" })
	ResponseEntity<Object> findAll();
}
