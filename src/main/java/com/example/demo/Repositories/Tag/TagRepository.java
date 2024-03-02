package com.example.demo.Repositories.Tag;

import com.example.demo.Entities.Tag;

import java.util.List;

public interface TagRepository {

    Tag addTag(Tag tag);
    List<Tag> allTags();
    Tag findTag(Integer id);
    Tag findTagByName(String name);
    Tag updateTag(Tag tag);
    void deleteTag(Integer id);
}
