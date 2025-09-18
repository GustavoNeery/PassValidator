package com.sensedia.sample.password.rest.repository;

import com.sensedia.sample.password.rest.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepository extends MongoRepository<User, Long> {
    User findByUsername(String username);
}
