package ru.xpressed.springreactservercoursework.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotEmpty(message = "E-Mail must not be empty!")
    @Email(regexp = "^[a-zA-Z\\.\\-\\_]*[@][a-zA-Z]*[\\.].[a-zA-Z]*", message = "E-Mail must be valid!")
    @Size(max = 40, message = "E-Mail must be less than 40 symbols!")
    private String username;

    @NotEmpty(message = "Surname must not be empty!")
    @Size(max = 20, message = "Surname must be less tan 15 symbols!")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]*", message = "Surname must contain only letters!")
    private String surname;

    @NotEmpty(message = "Name must not be empty!")
    @Size(max = 15, message = "Surname must be less than 15 symbols!")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]*", message = "Name must contain only letters!")
    private String name;

    @Size(max = 20, message = "Patronymic must be less than 20 symbols!")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]*", message = "Patronymic must contain only letters!")
    private String patronymic;

    @Size(max = 15, message = "Group Name must be less than 15 symbols!")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9\\-]*", message = "Group must contain only letters and numbers!")
    private String groupName;

    private String role;
}
