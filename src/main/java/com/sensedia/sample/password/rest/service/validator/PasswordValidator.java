package com.sensedia.sample.password.rest.service.validator;

import com.sensedia.sample.password.rest.dto.InvalidCasesDto;
import com.sensedia.sample.password.rest.dto.RegisterRequestDto;
import com.sensedia.sample.password.rest.entity.PasswordHistory;
import com.sensedia.sample.password.rest.entity.User;
import com.sensedia.sample.password.rest.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PasswordValidator {
    private static final String INITIAL_MESSAGE = "A senha deve conter pelo menos ";
    private static final String IS_SMALLER_THAN_8_CHARACTERS_MESSAGE = "8 caracteres";
    private static final String HAS_NO_CAPITAL_LETTER_MESSAGE = "uma letra maiúscula";
    private static final String IS_NOT_PASSWORD_MATCH_MESSAGE = "A senha de confirmação precisa ser igual a senha inserida";
    private static final String HAS_NO_LOWERCASE_LETTER_MESSAGE = "uma letra minúscula";
    private static final String HAS_NO_NUMBER_MESSAGE = "um número";
    private static final String HAS_NO_SPECIAL_CHARACTER_MESSAGE = "um caracatere especial";
    private static final String IS_COMMON_PASSWORD_MESSAGE = "Não pode ser uma senha comum";
    private static final String HAS_ONE_OF_LAST_FIVE_PASSWORDS_MESSAGE = "Não pode repetir suas últimas 5 senhas";
    private static final String HAS_USERNAME_IN_PASSWORD_MESSAGE = "A senha não pode conter o nome de usuário";
    private static final List<String> COMMON_PASSWORDS = List.of("12345678", "password");

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public PasswordValidator(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void validate(RegisterRequestDto registerRequestDto, User user) {
        if (isNotPasswordMatch(registerRequestDto)) {
            throw new InvalidPasswordException(List.of(IS_NOT_PASSWORD_MATCH_MESSAGE));
        }

        List<InvalidCasesDto> invalidCases = List.of(
                new InvalidCasesDto(this::isSmallerThan8Characters, INITIAL_MESSAGE + IS_SMALLER_THAN_8_CHARACTERS_MESSAGE),
                new InvalidCasesDto(this::hasNoCapitalLetter, INITIAL_MESSAGE + HAS_NO_CAPITAL_LETTER_MESSAGE),
                new InvalidCasesDto(this::isNotPasswordMatch,  IS_NOT_PASSWORD_MATCH_MESSAGE),
                new InvalidCasesDto(this::hasNoLowercaseLetter, INITIAL_MESSAGE + HAS_NO_LOWERCASE_LETTER_MESSAGE),
                new InvalidCasesDto(this::hasNoNumber, INITIAL_MESSAGE + HAS_NO_NUMBER_MESSAGE),
                new InvalidCasesDto(this::hasNoSpecialCharacter, INITIAL_MESSAGE + HAS_NO_SPECIAL_CHARACTER_MESSAGE),
                new InvalidCasesDto(this::isCommonPassword, IS_COMMON_PASSWORD_MESSAGE),
                new InvalidCasesDto((dto) -> hasOneOfLastFivePasswords(user, dto.password()),
                        HAS_ONE_OF_LAST_FIVE_PASSWORDS_MESSAGE
                ),
                new InvalidCasesDto(this::hasUsernameInPassword, HAS_USERNAME_IN_PASSWORD_MESSAGE)
        );

        List<String> errors = invalidCases.stream()
                .filter(invalidCase -> invalidCase.isInvalid(registerRequestDto))
                .map(InvalidCasesDto::getErrorMessage)
                .toList();

        if (!errors.isEmpty()) {
            List<String> finalErrors = formatErrorsByPrefix(errors);
            throw new InvalidPasswordException(finalErrors);
        }

    }

    private boolean isSmallerThan8Characters(RegisterRequestDto registerRequestDto) {
        return registerRequestDto.password().length() < 8;
    }

    private boolean hasNoCapitalLetter(RegisterRequestDto registerRequestDto) {
        return registerRequestDto.password().chars().noneMatch(Character::isUpperCase);
    }

    private boolean isNotPasswordMatch(RegisterRequestDto registerRequestDto) {
        return !registerRequestDto.password().equals(registerRequestDto.confirmPassword());
    }

    public boolean hasNoLowercaseLetter(RegisterRequestDto registerRequestDto) {
        return registerRequestDto.password().chars().noneMatch(Character::isLowerCase);
    }

    public boolean hasNoNumber(RegisterRequestDto registerRequestDto) {
        return registerRequestDto.password().chars().noneMatch(Character::isDigit);
    }

    public boolean hasNoSpecialCharacter(RegisterRequestDto registerRequestDto) {
        return registerRequestDto.password().chars().allMatch(Character::isLetterOrDigit);
    }

    public boolean isCommonPassword(RegisterRequestDto registerRequestDto) {
        return COMMON_PASSWORDS.contains(registerRequestDto.password());
    }

    private boolean hasOneOfLastFivePasswords(User userFound, String password) {
        if(userFound != null) {
            return userFound.getPasswordHistories()
                    .stream()
                    .map(PasswordHistory::getPassword).anyMatch(pwd -> bCryptPasswordEncoder.matches(password, pwd));
        }

        return false;
    }

    private boolean hasUsernameInPassword(RegisterRequestDto registerRequestDto) {
        return registerRequestDto.password().toLowerCase().contains(registerRequestDto.username().toLowerCase());
    }

    private List<String> formatErrorsByPrefix(List<String> errors) {
        List<String> finalErrors = new ArrayList<>();

        List<String> initialMessage = errors.stream()
                .filter(msg -> msg.startsWith(INITIAL_MESSAGE))
                .map(msg -> msg.replace(INITIAL_MESSAGE, "").trim())
                .toList();

        if (!initialMessage.isEmpty()) {
            String joined = String.join(", ", initialMessage);
            finalErrors.add(INITIAL_MESSAGE + joined);
        }

        errors.stream()
                .filter(msg -> !msg.startsWith(INITIAL_MESSAGE))
                .forEach(finalErrors::add);

        return finalErrors;
    }

}
