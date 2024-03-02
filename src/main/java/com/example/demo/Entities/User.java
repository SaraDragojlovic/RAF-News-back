package com.example.demo.Entities;

import com.example.demo.Entities.Enums.Status;
import com.example.demo.Entities.Enums.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class User {

    private int id;

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

    @NotNull(message = "Status field is required")
    private Status status;

    @NotNull(message = "Password hash field is required")
    @NotEmpty(message = "Password hash field is required")
    private String password_hash;

    public User() {

    }

    public User(String email, String first_name, String last_name, UserType user_type, Status status, String password_hash) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_type = user_type;
        this.status = status;
        this.password_hash = password_hash;
    }

    public User(Integer id, String email, String first_name, String last_name, UserType user_type, Status status, String password_hash) {
        this(email, first_name, last_name, user_type, status, password_hash);
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", user_type=" + user_type +
                ", status=" + status +
                ", password_hash='" + password_hash + '\'' +
                '}';
    }
}
