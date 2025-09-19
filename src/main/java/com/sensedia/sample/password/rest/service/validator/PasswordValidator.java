package com.sensedia.sample.password.rest.service.validator;

import com.sensedia.sample.password.rest.dto.InvalidCasesDto;
import com.sensedia.sample.password.rest.dto.RegisterRequestDto;
import com.sensedia.sample.password.rest.exception.InvalidPasswordException;

import java.util.List;

public class PasswordValidator {
    private static final String INITIAL_MESSAGE = "A senha deve conter pelo menos ";
    private static final String IS_SMALLER_THAN_8_CHARACTERS_MESSAGE = "8 caracteres.";
    private static final String HAS_NO_CAPITAL_LETTER_MESSAGE = "uma letra maiúscula.";


    public void validate(RegisterRequestDto registerRequestDto) {
        List<InvalidCasesDto> invalidCases = List.of(
                new InvalidCasesDto(this::isSmallerThan8Characters, INITIAL_MESSAGE + IS_SMALLER_THAN_8_CHARACTERS_MESSAGE),
                new InvalidCasesDto(this::hasNoCapitalLetter, INITIAL_MESSAGE + HAS_NO_CAPITAL_LETTER_MESSAGE)
        );

        List<String> errors = invalidCases.stream()
                .filter(invalidCase -> invalidCase.isInvalid(registerRequestDto))
                .map(InvalidCasesDto::getErrorMessage)
                .toList();

        if (!errors.isEmpty()) {
            throw new InvalidPasswordException(errors);
        }
    }

    private boolean isSmallerThan8Characters(RegisterRequestDto registerRequestDto) {
        return registerRequestDto.password().length() < 8;
    }

    private boolean hasNoCapitalLetter(RegisterRequestDto registerRequestDto) {
        return registerRequestDto.password().chars().noneMatch(Character::isUpperCase);
    }
}
