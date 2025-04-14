package com.study.user_service.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLogin {

    @NotNull(message = "Email cannot be null.")
    @Size(min = 2, message = "Email not be less than 2 characters.")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 2, message = "Password must be equals or grater than 8 characters.")
    private String password;

}
