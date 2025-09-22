package com.sensedia.sample.password.rest.service.user;

import com.sensedia.sample.password.rest.dto.RegisterRequestDto;
import com.sensedia.sample.password.rest.entity.PasswordHistory;
import com.sensedia.sample.password.rest.entity.User;
import com.sensedia.sample.password.rest.repository.IUserRepository;
import com.sensedia.sample.password.rest.service.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements IUserService {
    private static final int MAX_PASSWORD_HISTORY_SIZE = 5;

    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordValidator passwordValidator;

    @Autowired
    public UserService(IUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordValidator = new PasswordValidator(bCryptPasswordEncoder);
    }

    @Override
    public User invokeCreateOrUpdate(RegisterRequestDto registerRequestDto) {
        final User userFound = findByUsername(registerRequestDto.username());
        final String encodedPassword = bCryptPasswordEncoder.encode(registerRequestDto.password());

        passwordValidator.validate(registerRequestDto, userFound);

        if(userFound == null) {
            return create(registerRequestDto, encodedPassword);
        }

        return update(userFound, encodedPassword);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    private User create(RegisterRequestDto registerRequestDto, String encodedPassword) {
        final User newUser = new User(
                registerRequestDto.username(),
                encodedPassword,
                List.of(createPasswordHistory(encodedPassword))
        );

        return userRepository.save(newUser);
    }

    private User update(User userFound, String encodedPassword) {
        maintainPasswordHistoryLimit(userFound);
        userFound.setPassword(encodedPassword);
        userFound.getPasswordHistories().add(createPasswordHistory(encodedPassword));

        return userRepository.save(userFound);
    }

    private void maintainPasswordHistoryLimit(User userFound) {
        if(userFound.getPasswordHistories().size() >= MAX_PASSWORD_HISTORY_SIZE) {
            userFound.getPasswordHistories().removeFirst();
        }
    }

    private PasswordHistory createPasswordHistory(String password) {
        return new PasswordHistory(password, LocalDateTime.now());
    }
}
