package com.study.user_service.dto;

import com.study.user_service.vo.ResponseOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
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

    private List<ResponseOrder> orders;

    public void create(String encryptedPassword) {
        this.userId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.encryptedPassword = encryptedPassword; // temp
    }
}
