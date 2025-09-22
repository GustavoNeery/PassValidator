package com.sensedia.sample.password.rest.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Objects;

@Document(collection = "users")
public class User {

    @MongoId
    private String id;

    @Setter
    @Getter
    private String username;

    @Setter
    @Getter
    private String password;

    @Setter
    @Getter
    private List<PasswordHistory> passwordHistories;

    public User() {
        
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, List<PasswordHistory> passwordHistoryList) {
        this.username = username;
        this.password = password;
        this.passwordHistories = passwordHistoryList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
