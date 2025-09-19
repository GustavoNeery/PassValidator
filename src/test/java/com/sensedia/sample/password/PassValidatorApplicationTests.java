package com.sensedia.sample.password;

import com.sensedia.sample.password.rest.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

@Import(TestContainersConfiguration.class)
@SpringBootTest
class PassValidatorApplicationTests {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @MockitoSpyBean
    private UserService userService;

	@Test
	void contextLoads() {
	}

    @Test
    void shouldRegisterNewUser() {

    }

}
