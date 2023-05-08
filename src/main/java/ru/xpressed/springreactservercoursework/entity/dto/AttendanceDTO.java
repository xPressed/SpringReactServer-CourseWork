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
public class AttendanceDTO {
    private Integer id;

    @NotEmpty(message = "Date must not be empty!")
    @Pattern(regexp = "^[0-3][0-9].[0-1][0-9].[0-9][0-9][0-9][0-9]", message = "Date must be DD.MM.YYYY")
    private String date;

    @NotEmpty(message = "Enter Time must not be empty!")
    @Pattern(regexp = "^[0-2][0-9]:[0-5][0-9]", message = "Enter Time must be HH:MM")
    private String enterTime;

    @NotEmpty(message = "Exit Time must not be empty!")
    @Pattern(regexp = "^[0-2][0-9]:[0-5][0-9]", message = "Exit Time must be HH:MM")
    private String exitTime;

    @NotEmpty(message = "E-Mail must not be empty!")
    @Email(regexp = "^[a-zA-Z\\.\\-\\_]*[@][a-zA-Z]*[\\.].[a-zA-Z]*", message = "E-Mail must be valid!")
    @Size(max = 40, message = "E-Mail must be less than 40 symbols!")
    private String username;
}
