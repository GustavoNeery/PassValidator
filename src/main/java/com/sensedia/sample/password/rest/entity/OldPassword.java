package com.sensedia.sample.password.rest.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class OldPassword {

    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public OldPassword(String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
