package com.kodit.application.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "username Cannot be empty..!")
    private String username;

    @NotEmpty(message = "password Cannot be empty..!")
    private String password;

}
