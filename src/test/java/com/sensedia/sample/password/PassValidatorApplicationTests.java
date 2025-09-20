package com.sensedia.sample.password;

import com.sensedia.sample.password.rest.dto.RegisterRequestDto;
import com.sensedia.sample.password.rest.entity.User;
import com.sensedia.sample.password.rest.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

@Import(TestContainersConfiguration.class)
@SpringBootTest
class PassValidatorApplicationTests {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "Senha123";
    private static final String CONFIRM_PASSWORD = "Senha123";

    @MockitoSpyBean
    private UserService userService;

    @Test
    @DisplayName("Should create new user with success")
    void shouldRegisterNewUserWithSuccess() {
        Mockito.when(userService.findByUsername(USERNAME))
                .thenReturn(null);

        User userCreated = userService.register(new RegisterRequestDto(USERNAME, PASSWORD, CONFIRM_PASSWORD));

        Assertions.assertNotNull(userCreated);
        Assertions.assertEquals(USERNAME, userCreated.getUsername());
    }
}
