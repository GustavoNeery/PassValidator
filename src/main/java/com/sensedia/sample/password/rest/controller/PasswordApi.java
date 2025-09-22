package com.sensedia.sample.password.rest.controller;

import com.sensedia.sample.password.rest.dto.RegisterRequestDto;
import com.sensedia.sample.password.rest.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

    @Controller
@Slf4j
public class PasswordApi implements IPasswordApi {

    private final UserService userService;

    @Autowired
    public PasswordApi(UserService userService) {
        this.userService = userService;
    }

	@Override
	public ResponseEntity<Object> findAll() {
		log.info("Requisição recebida!!!!!!");
		return ResponseEntity.badRequest().body("Dummy");
	}

    @Override
    public ResponseEntity<Object> createOrUpdate(RegisterRequestDto registerRequestDto) {
        userService.invokeCreateOrUpdate(registerRequestDto);
        return ResponseEntity.noContent().build();
    }

}
