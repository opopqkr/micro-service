package com.study.user_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {

    private String email;

    private String name;

    private String password;

    private String userId;

    private LocalDateTime createdAt;

    private String encryptedPassword;

    public void create(String encryptedPassword) {
        this.userId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.encryptedPassword = encryptedPassword; // temp
    }
}
