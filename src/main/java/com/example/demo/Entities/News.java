package com.example.demo.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class News {

    private int id;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;

    @NotNull(message = "Text field is required")
    @NotEmpty(message = "Text field is required")
    private String text;

    private String creation_time;

    private int visit_count;

    @NotNull(message = "Author field is required")
    private int author_id;

    @NotNull(message = "Category field is required")
    private int category_id;

    public News() {

    }

    public News(String title, String text, Integer author_id, Integer category_id) {
        this.title = title;
        this.text = text;
        this.author_id = author_id;
        this.category_id = category_id;
        this.visit_count = 0;
    }

    public News(Integer id, String title, String text, Integer author_id, Integer category_id) {
        this(title, text, author_id, category_id);
        this.id = id;
    }
}
