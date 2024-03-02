package com.example.demo.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Comment {

    private int id;

    @NotNull(message = "Author name field is required")
    @NotEmpty(message = "Author name field is required")
    private String author_name;

    @NotNull(message = "Text field is required")
    @NotEmpty(message = "Text field is required")
    private String text;

    private String creation_date;

    @NotNull(message = "News id field is required")
    private int news_id;

    public Comment() {

    }

    public Comment(String author_name, String text, Integer news_id) {
        this.author_name = author_name;
        this.text = text;
        this.news_id = news_id;
    }

    public Comment(int id, String author_name, String text, Integer news_id) {
        this(author_name, text, news_id);
        this.id = id;
    }
}
