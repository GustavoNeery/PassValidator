package com.sensedia.sample.password.rest.controller;

import com.sensedia.sample.password.rest.dto.RegisterRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IPasswordApi {

	@GetMapping(value = "/password-validations", produces = { "application/json" })
	ResponseEntity<Object> findAll();

    @PostMapping(value = "/password-validations", produces = { "application/json" })
    ResponseEntity<Object> register(@RequestBody RegisterRequestDto registerRequestDto);
}
