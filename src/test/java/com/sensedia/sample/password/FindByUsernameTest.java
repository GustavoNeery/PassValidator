package com.sensedia.sample.password;

import com.sensedia.sample.password.rest.entity.OldPassword;
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
    private OldPassword oldPassword;

    @BeforeEach
    void setUp() {
        oldPassword = makeOldPassword(PASSWORD);
        expectedUser = makeUser();
    }

    @Test
    @DisplayName("Should find user by username")
    void shouldFindByUsernameWithSuccess() {
        User userFound = userService.findByUsername(USERNAME);
        Assertions.assertEquals(expectedUser, userFound);
    }

    private User makeUser() {
        List<OldPassword> oldPasswords = new ArrayList<>();
        oldPasswords.add(oldPassword);

        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setOldPasswords(oldPasswords);

        return userService.save(user);
    }

    private OldPassword makeOldPassword(String password) {
        return new OldPassword(password, LocalDateTime.now());
    }
}
