package com.example.demo.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Category {

    private int id;

    @NotNull(message = "Name field is required")
    @NotEmpty(message = "Name field is required")
    private String name;

    @NotNull(message = "Description field is required")
    @NotEmpty(message = "Description field is required")
    private String description;

    public Category() {

    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Category(Integer id, String name, String description) {
        this(name, description);
        this.id = id;
    }
}
