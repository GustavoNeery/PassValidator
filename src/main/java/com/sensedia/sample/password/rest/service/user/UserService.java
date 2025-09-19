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
    public void register(RegisterRequestDto registerRequestDto) {
        passwordValidator.validate(registerRequestDto);
        User userFound = findByUsername(registerRequestDto.username());

        if(userFound == null) {
            create(registerRequestDto);
        } else {
            update(userFound, registerRequestDto);
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private void create(RegisterRequestDto registerRequestDto) {
        final String encodedPassword = bCryptPasswordEncoder.encode(registerRequestDto.password());
        User newUser = new User(
                registerRequestDto.username(),
                encodedPassword,
                List.of(createOldPassword(encodedPassword))
        );
        userRepository.save(newUser);
    }

    private void update(User userFound, RegisterRequestDto registerRequestDto) {
        final String encodedPassword = bCryptPasswordEncoder.encode(registerRequestDto.password());
        userFound.setPassword(encodedPassword);
        userFound.getOldPasswords().add(createOldPassword(encodedPassword));
        userRepository.save(userFound);
    }

    public OldPassword createOldPassword(String password) {
        return new OldPassword(password, LocalDateTime.now());
    }
}
