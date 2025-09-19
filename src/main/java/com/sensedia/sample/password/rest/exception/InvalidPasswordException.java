package com.sensedia.sample.password.rest.exception;

import java.util.List;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(List<String> messages) {
        super(String.join(" ", messages));
    }
}
