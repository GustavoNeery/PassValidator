package com.sensedia.sample.password;

import com.sensedia.sample.password.rest.dto.RegisterRequestDto;
import com.sensedia.sample.password.rest.entity.OldPassword;
import com.sensedia.sample.password.rest.entity.User;
import com.sensedia.sample.password.rest.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Import(TestContainersConfiguration.class)
@SpringBootTest
public class UpdateUserWithPasswordHistoryLimitTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD1 = "Nova1234";
    private static final String PASSWORD2 = "Novb1234";
    private static final String PASSWORD3 = "Novc1234";
    private static final String PASSWORD4 = "Novd1234";
    private static final String PASSWORD5 = "Nove1234";
    private static final String NEW_PASSWORD6 = "NovF1234";
    private static final String NEW_CONFIRM_PASSWORD6 = "NovF1234";
    private static final int NUMBER_OF_PASSWORDS = 5;

    @MockitoSpyBean
    private UserService userService;

    private User user;
    List<OldPassword> oldPasswordsUser;

    @BeforeEach
    void setUp() {
        oldPasswordsUser = new ArrayList<>();
        oldPasswordsUser.add(makeOldPassword(PASSWORD1));
        oldPasswordsUser.add(makeOldPassword(PASSWORD2));
        oldPasswordsUser.add(makeOldPassword(PASSWORD3));
        oldPasswordsUser.add(makeOldPassword(PASSWORD4));
        oldPasswordsUser.add(makeOldPassword(PASSWORD5));
        user = makeUser(oldPasswordsUser);
    }

    @Test
    @DisplayName("Should update user with password history limit and keep 5 in List")
    void shouldUpdateUserWithPasswordHistoryLimitWithSuccess() {
        Mockito.when(userService.findByUsername(USERNAME))
                .thenReturn(user);

        User userUpdated = userService.register(new RegisterRequestDto(USERNAME, NEW_PASSWORD6, NEW_CONFIRM_PASSWORD6));

        Assertions.assertEquals(USERNAME, userUpdated.getUsername());
        Assertions.assertEquals(NUMBER_OF_PASSWORDS, userUpdated.getOldPasswords().size());
    }

    private User makeUser(List<OldPassword> oldPasswords) {
        User user = new User();

        user.setUsername(USERNAME);
        user.setPassword(PASSWORD1);
        user.setOldPasswords(oldPasswords);

        return userService.save(user);
    }

    private OldPassword makeOldPassword(String NEW_PASSWORD)
    {
        return new OldPassword(NEW_PASSWORD, LocalDateTime.now());
    }
}
