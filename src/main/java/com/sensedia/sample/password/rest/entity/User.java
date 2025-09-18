package com.sensedia.sample.password.rest.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

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
    private List<OldPassword> oldPasswords;

    public User() {
        
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, List<OldPassword> oldPasswordList) {
        this.username = username;
        this.password = password;
        this.oldPasswords = oldPasswordList;
    }

}
