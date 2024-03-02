package com.example.demo.Dto;

import com.example.demo.Entities.Enums.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserUpdateDTO {

    @NotNull(message = "Email field is required")
    @NotEmpty(message = "Email field is required")
    private String email;

    @NotNull(message = "First name field is required")
    @NotEmpty(message = "First name field is required")
    private String first_name;

    @NotNull(message = "Last name field is required")
    @NotEmpty(message = "Last name field is required")
    private String last_name;

    @NotNull(message = "User type field is required")
    private UserType user_type;

    public UserUpdateDTO() {

    }

    public UserUpdateDTO(String email, String first_name, String last_name, UserType user_type) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_type = user_type;
    }
}

