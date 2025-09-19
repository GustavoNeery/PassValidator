package com.sensedia.sample.password.rest.dto;

import java.util.function.Predicate;

public class InvalidCasesDto {
    private final Predicate<RegisterRequestDto> invalidPredicate;
    private final String errorMessage;

    public InvalidCasesDto(Predicate<RegisterRequestDto> invalidPredicate, String errorMessage) {
        this.invalidPredicate = invalidPredicate;
        this.errorMessage = errorMessage;
    }

    public boolean isInvalid(RegisterRequestDto data) {
        return invalidPredicate.test(data);
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
