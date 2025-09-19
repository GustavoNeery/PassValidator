package com.sensedia.sample.password.rest.service.user;

import com.sensedia.sample.password.rest.dto.RegisterRequestDto;
import com.sensedia.sample.password.rest.entity.User;

public interface IUserService {

    User register(RegisterRequestDto registerRequestDto);

    User findByUsername(String username);

    User save(User user);
}
