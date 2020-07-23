package com.gloresoft.weatherapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@JsonIgnoreProperties
public class UserDto {
    @NotEmpty(message = "{username.required}")
    @Email(message = "{username.invalid}")
    private String username;

    @NotEmpty(message = "{password.required}")
    @Size(min = 4, message = "{password.too.short}")
    private String password;

    @NotNull(message = "{dob.required}")
    private Date dateOfBirth;
}
