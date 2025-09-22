package com.sensedia.sample.password;

import com.sensedia.sample.password.rest.entity.PasswordHistory;
import com.sensedia.sample.password.rest.entity.User;
import com.sensedia.sample.password.rest.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Import(TestContainersConfiguration.class)
@SpringBootTest
public class FindByUsernameTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "Senha123";

    @Autowired
    private UserService userService;

    private User expectedUser;
    private PasswordHistory passwordHistory;

    @BeforeEach
    void setUp() {
        passwordHistory = makeOldPassword(PASSWORD);
        expectedUser = makeUser();
    }

    @Test
    @DisplayName("Should find user by username")
    void shouldFindByUsernameWithSuccess() {
        User userFound = userService.findByUsername(USERNAME);
        Assertions.assertEquals(expectedUser, userFound);
    }

    private User makeUser() {
        List<PasswordHistory> passwordHistories = new ArrayList<>();
        passwordHistories.add(passwordHistory);

        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setPasswordHistories(passwordHistories);

        return userService.save(user);
    }

    private PasswordHistory makeOldPassword(String password) {
        return new PasswordHistory(password, LocalDateTime.now());
    }
}
