package com.sensedia.sample.password;

import com.sensedia.sample.password.rest.dto.RegisterRequestDto;
import com.sensedia.sample.password.rest.entity.OldPassword;
import com.sensedia.sample.password.rest.entity.User;
import com.sensedia.sample.password.rest.service.user.UserService;
import org.junit.Before;
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
class PassValidatorApplicationTests {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "Senha123";
    private static final String CONFIRM_PASSWORD = "Senha123";
    private static final String NEW_PASSWORD = "Nova1234";
    private static final String NEW_CONFIRM_PASSWORD = "Nova1234";
    private static final int NUMBER_OF_PASSWORDS = 2;

    @MockitoSpyBean
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User user1;
    private OldPassword oldPassword1;

    @BeforeEach
    void setUp() {
        user1 = makeUser();
        oldPassword1 = makeOldPassword(NEW_PASSWORD);
    }

	@Test
	void contextLoads() {
	}

    @Test
    @DisplayName("Should create new user with success")
    void shouldRegisterNewUserWithSuccess() {
        Mockito.when(userService.findByUsername(USERNAME))
                .thenReturn(null);

        User userCreated = userService.register(new RegisterRequestDto(USERNAME, PASSWORD, CONFIRM_PASSWORD));

        Assertions.assertNotNull(userCreated);
        Assertions.assertEquals(USERNAME, userCreated.getUsername());
    }

    @Test
    @DisplayName("Should update user password with success")
    void shouldUpdateUserPasswordWithSuccess() {
        Mockito.when(userService.findByUsername(USERNAME))
                .thenReturn(user1);

        User userUpdated = userService.register(new RegisterRequestDto(USERNAME, NEW_PASSWORD, NEW_CONFIRM_PASSWORD));

        Assertions.assertNotNull(userUpdated);
        Assertions.assertEquals(USERNAME, userUpdated.getUsername());
        Assertions.assertTrue(isPasswordMatch(NEW_PASSWORD, userUpdated.getPassword()));
        Assertions.assertEquals(NUMBER_OF_PASSWORDS, userUpdated.getOldPasswords().size());
    }

    private boolean isPasswordMatch(String NEW_PASSWORD, String savedPassword) {
        return bCryptPasswordEncoder.matches(NEW_PASSWORD, savedPassword);
    }

    private User makeUser() {
        List<OldPassword> oldPasswords = new ArrayList<>();
        oldPasswords.add(oldPassword1);

        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setOldPasswords(oldPasswords);

        return userService.save(user);
    }

    private OldPassword makeOldPassword(String NEW_PASSWORD)
    {
        return new OldPassword(NEW_PASSWORD, LocalDateTime.now());
    }
}
