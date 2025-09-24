package com.sensedia.sample.password.rest.service.user;

import com.sensedia.sample.password.rest.dto.RegisterRequestDto;
import com.sensedia.sample.password.rest.entity.PasswordHistory;
import com.sensedia.sample.password.rest.entity.User;

import java.util.List;

public interface IUserService {

    User invokeCreateOrUpdate(RegisterRequestDto registerRequestDto);

    User findByUsername(String username);

    User save(User user);

    List<PasswordHistory> findPasswordHistoryByUsername(String username);

}
