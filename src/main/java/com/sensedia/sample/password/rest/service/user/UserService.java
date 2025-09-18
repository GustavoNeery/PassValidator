package com.sensedia.sample.password.rest.service.user;

import com.sensedia.sample.password.rest.dto.RegisterRequestDto;
import com.sensedia.sample.password.rest.entity.User;
import com.sensedia.sample.password.rest.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(RegisterRequestDto registerRequestDto) {
        User userFound = findByUsername(registerRequestDto.username());

        if(userFound == null) {
            User newUser = new User(registerRequestDto.username(), registerRequestDto.password());
            userRepository.save(newUser);
        }
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
