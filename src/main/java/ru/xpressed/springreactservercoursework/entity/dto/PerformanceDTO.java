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
public class PerformanceDTO {
    private Integer id;

    @NotEmpty(message = "Discipline name must not be empty!")
    @Size(max = 75, message = "Discipline name must be less than 75 symbols!")
    private String name;

    @NotEmpty(message = "Mark must not be empty!")
    @Size(min = 1, max = 1, message = "Mark must be valid!")
    private String mark;

    @NotEmpty(message = "Year must not be empty!")
    @Size(min = 4, max = 4, message = "Year must contain 4 symbols!")
    @Pattern(regexp = "^[0-9]*", message = "Year must contain only numbers!")
    private String year;

    @NotEmpty(message = "E-Mail must not be empty!")
    @Email(regexp = "^[a-zA-Z\\.\\-\\_]*[@][a-zA-Z]*[\\.].[a-zA-Z]*", message = "E-Mail must be valid!")
    @Size(max = 40, message = "E-Mail must be less than 40 symbols!")
    private String username;
}
