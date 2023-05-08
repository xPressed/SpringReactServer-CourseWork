package ru.xpressed.springreactservercoursework.entity.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class LoginDTO {
    @NotEmpty(message = "Username must not be empty!")
    private String username;

    @NotEmpty(message = "Password must not be empty!")
    private String password;
}
