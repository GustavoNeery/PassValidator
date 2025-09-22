package com.sensedia.sample.password;

import com.sensedia.sample.password.rest.dto.RegisterRequestDto;
import com.sensedia.sample.password.rest.exception.InvalidPasswordException;
import com.sensedia.sample.password.rest.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestContainersConfiguration.class)
@SpringBootTest
class PassValidatorApplicationTests {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "senhaqualquer";

    private static final String PASSWORD_IS_SMALLER_THAN_8_CHARACTERS = "Se-123";
    private static final String IS_SMALLER_THAN_8_CHARACTERS_MESSAGE = "A senha deve conter pelo menos 8 caracteres.";

    private static final String PASSWORD_HAS_NO_CAPITAL_LETTER = "senh-123";
    private static final String HAS_NO_CAPITAL_LETTER_MESSAGE = "A senha deve conter pelo menos uma letra maiúscula.";

    private static final String PASSWORD_IS_NOT_PASSWORD_MATCH = "senh-123";
    private static final String IS_NOT_PASSWORD_MATCH_MESSAGE = "A senha de confirmação precisa ser igual a senha inserida.";

    private static final String PASSWORD_HAS_NO_LOWERCASE_LETTER = "SENH-123";
    private static final String HAS_NO_LOWERCASE_LETTER_MESSAGE = "A senha deve conter pelo menos uma letra minúscula.";

    private static final String PASSWORD_HAS_NO_NUMBER = "Senh----";
    private static final String HAS_NO_NUMBER_MESSAGE = "A senha deve conter pelo menos um número.";

    private static final String PASSWORD_HAS_NO_SPECIAL_CHARACTER = "Senha1234";
    private static final String HAS_NO_SPECIAL_CHARACTER_MESSAGE = "A senha deve conter pelo menos um caracatere especial.";

    private static final String IS_COMMON_PASSWORD = "12345678";

    private static final String HAS_USERNAME_IN_PASSWORD = "usernameA-123";
    private static final String HAS_USERNAME_IN_PASSWORD_MESSAGE = "A senha não pode conter o nome de usuário.";

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Should throw exception when password is smaller than 8 characters")
    void shouldThrowExceptionWhenPasswordIsSmallerThan8Characters() {
        InvalidPasswordException exception = Assertions.assertThrows(InvalidPasswordException.class, () -> userService.invokeCreateOrUpdate(new RegisterRequestDto(USERNAME, PASSWORD_IS_SMALLER_THAN_8_CHARACTERS, PASSWORD_IS_SMALLER_THAN_8_CHARACTERS)));
        Assertions.assertEquals(IS_SMALLER_THAN_8_CHARACTERS_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when password has no capital letter")
    void shouldThrowExceptionWhenPasswordHasNoCapitalLetter() {
        InvalidPasswordException exception = Assertions.assertThrows(
                InvalidPasswordException.class,
                () -> userService.invokeCreateOrUpdate(new RegisterRequestDto(USERNAME, PASSWORD_HAS_NO_CAPITAL_LETTER, PASSWORD_HAS_NO_CAPITAL_LETTER))
        );
        Assertions.assertEquals(HAS_NO_CAPITAL_LETTER_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when password and confirm password do not match")
    void shouldThrowExceptionWhenPasswordAndConfirmPasswordDoNotMatch() {
        InvalidPasswordException exception = Assertions.assertThrows(
                InvalidPasswordException.class,
                () -> userService.invokeCreateOrUpdate(new RegisterRequestDto(USERNAME, PASSWORD_IS_NOT_PASSWORD_MATCH, PASSWORD))
        );
        Assertions.assertEquals(IS_NOT_PASSWORD_MATCH_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when password has no lowercase letter")
    void shouldThrowExceptionWhenPasswordHasNoLowercaseLetter() {
        InvalidPasswordException exception = Assertions.assertThrows(
                InvalidPasswordException.class,
                () -> userService.invokeCreateOrUpdate(new RegisterRequestDto(USERNAME, PASSWORD_HAS_NO_LOWERCASE_LETTER, PASSWORD_HAS_NO_LOWERCASE_LETTER))
        );
        Assertions.assertEquals(HAS_NO_LOWERCASE_LETTER_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when password has no number")
    void shouldThrowExceptionWhenPasswordHasNoNumber() {
        InvalidPasswordException exception = Assertions.assertThrows(
                InvalidPasswordException.class,
                () -> userService.invokeCreateOrUpdate(new RegisterRequestDto(USERNAME, PASSWORD_HAS_NO_NUMBER, PASSWORD_HAS_NO_NUMBER))
        );
        Assertions.assertEquals(HAS_NO_NUMBER_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when password has no special character")
    void shouldThrowExceptionWhenPasswordHasNoSpecialCharacter() {
        InvalidPasswordException exception = Assertions.assertThrows(
                InvalidPasswordException.class,
                () -> userService.invokeCreateOrUpdate(new RegisterRequestDto(USERNAME, PASSWORD_HAS_NO_SPECIAL_CHARACTER, PASSWORD_HAS_NO_SPECIAL_CHARACTER))
        );
        Assertions.assertEquals(HAS_NO_SPECIAL_CHARACTER_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when password is a common password")
    void shouldThrowExceptionWhenPasswordIsCommonPassword() {
        Assertions.assertThrows(
                InvalidPasswordException.class,
                () -> userService.invokeCreateOrUpdate(new RegisterRequestDto(USERNAME, IS_COMMON_PASSWORD, IS_COMMON_PASSWORD))
        );
    }

    @Test
    @DisplayName("Should throw exception when password contains username")
    void shouldThrowExceptionWhenPasswordContainsUsername() {
        InvalidPasswordException exception = Assertions.assertThrows(
                InvalidPasswordException.class,
                () -> userService.invokeCreateOrUpdate(new RegisterRequestDto(USERNAME, HAS_USERNAME_IN_PASSWORD, HAS_USERNAME_IN_PASSWORD))
        );
        Assertions.assertEquals(HAS_USERNAME_IN_PASSWORD_MESSAGE, exception.getMessage());
    }
}
