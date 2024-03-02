package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NewsUpdateDTO {
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;

    @NotNull(message = "Text field is required")
    @NotEmpty(message = "Text field is required")
    private String text;

    @NotNull(message = "Category field is required")
    private int category_id;
}
