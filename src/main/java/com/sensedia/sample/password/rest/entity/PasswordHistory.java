package com.sensedia.sample.password.rest.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PasswordHistory {

    private String password;

    private LocalDateTime createdAt;

    public PasswordHistory(String password, LocalDateTime createdAt) {
        this.password = password;
        this.createdAt = createdAt;
    }

}
