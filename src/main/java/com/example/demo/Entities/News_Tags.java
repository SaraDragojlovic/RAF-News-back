package com.example.demo.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class News_Tags {

    @NotNull(message = "News ID field is required")
    private int news_id;

    @NotNull(message = "Tag ID field is required")
    private int tag_id;

    public News_Tags() {

    }

    public News_Tags(int news_id, int tag_id) {
        this.news_id = news_id;
        this.tag_id = tag_id;
    }
}
