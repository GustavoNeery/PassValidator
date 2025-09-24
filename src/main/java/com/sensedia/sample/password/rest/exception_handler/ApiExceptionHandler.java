package com.sensedia.sample.password.rest.exception_handler;

import com.sensedia.sample.password.rest.dto.ErrorResponseDto;
import com.sensedia.sample.password.rest.exception.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleInvalidPassword(InvalidPasswordException ex) {
        return new ErrorResponseDto("Senha inválida", ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleInvalidArgument(IllegalArgumentException ex) {
        return new ErrorResponseDto("Usuário não existe", ex.getMessage());
    }
}

