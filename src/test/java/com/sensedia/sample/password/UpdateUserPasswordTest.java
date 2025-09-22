package com.sensedia.sample.password;

import com.sensedia.sample.password.rest.dto.RegisterRequestDto;
import com.sensedia.sample.password.rest.entity.PasswordHistory;
import com.sensedia.sample.password.rest.entity.User;
import com.sensedia.sample.password.rest.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Import(TestContainersConfiguration.class)
@SpringBootTest
public class UpdateUserPasswordTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "Senha123";
    private static final String NEW_PASSWORD = "Nova1234";
    private static final String NEW_CONFIRM_PASSWORD = "Nova1234";
    private static final int NUMBER_OF_PASSWORDS = 2;

    @MockitoSpyBean
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User user1;
    private PasswordHistory passwordHistory1;

    @BeforeEach
    void setUp() {
        passwordHistory1 = makeOldPassword(NEW_PASSWORD);
        user1 = makeUser();
    }

    @Test
    @DisplayName("Should update user password with success")
    void shouldUpdateUserPasswordWithSuccess() {
        Mockito.when(userService.findByUsername(USERNAME))
                .thenReturn(user1);

        User userUpdated = userService.invokeCreateOrUpdate(new RegisterRequestDto(USERNAME, NEW_PASSWORD, NEW_CONFIRM_PASSWORD));

        Assertions.assertNotNull(userUpdated);
        Assertions.assertEquals(USERNAME, userUpdated.getUsername());
        Assertions.assertTrue(isPasswordMatch(NEW_PASSWORD, userUpdated.getPassword()));
        Assertions.assertEquals(NUMBER_OF_PASSWORDS, userUpdated.getPasswordHistories().size());
    }

    private boolean isPasswordMatch(String NEW_PASSWORD, String savedPassword) {
        return bCryptPasswordEncoder.matches(NEW_PASSWORD, savedPassword);
    }

    private User makeUser() {
        List<PasswordHistory> passwordHistories = new ArrayList<>();
        passwordHistories.add(passwordHistory1);

        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setPasswordHistories(passwordHistories);

        return userService.save(user);
    }

    private PasswordHistory makeOldPassword(String NEW_PASSWORD)
    {
        return new PasswordHistory(NEW_PASSWORD, LocalDateTime.now());
    }
}
