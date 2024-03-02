package com.example.demo.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Tag {

    private int id;

    @NotNull(message = "Name field is required")
    @NotEmpty(message = "Name field is required")
    private String name;

    public Tag() {

    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag(Integer id, String name) {
        this(name);
        this.id = id;
    }
}
