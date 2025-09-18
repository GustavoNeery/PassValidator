package com.sensedia.sample.password.rest.service.user;

import com.sensedia.sample.password.rest.entity.User;

public interface IUserService {

    User save(User user);

    User findByUsername(String username);
}
