package com.sensedia.sample.password.rest.service.user;

import com.sensedia.sample.password.rest.dto.RegisterRequestDto;
import com.sensedia.sample.password.rest.entity.OldPassword;
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
        this.passwordValidator = new PasswordValidator();
    }

    @Override
    public User register(RegisterRequestDto registerRequestDto) {
        passwordValidator.validate(registerRequestDto);
        User userFound = findByUsername(registerRequestDto.username());

        if(userFound == null) {
            return create(registerRequestDto);
        }

        return update(userFound, registerRequestDto);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    private User create(RegisterRequestDto registerRequestDto) {
        final String encodedPassword = bCryptPasswordEncoder.encode(registerRequestDto.password());

        User newUser = new User(
                registerRequestDto.username(),
                encodedPassword,
                List.of(createOldPassword(encodedPassword))
        );

        return userRepository.save(newUser);
    }

    private User update(User userFound, RegisterRequestDto registerRequestDto) {
        maintainPasswordHistoryLimit(userFound);
        final String encodedPassword = bCryptPasswordEncoder.encode(registerRequestDto.password());

        userFound.setPassword(encodedPassword);
        userFound.getOldPasswords().add(createOldPassword(encodedPassword));

        return userRepository.save(userFound);
    }

    private void maintainPasswordHistoryLimit(User userFound) {
        if(userFound.getOldPasswords().size() >= MAX_PASSWORD_HISTORY_SIZE) {
            userFound.getOldPasswords().removeFirst();
        }
    }

    private OldPassword createOldPassword(String password) {
        return new OldPassword(password, LocalDateTime.now());
    }
}
