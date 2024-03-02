package com.example.demo.Services;

import com.example.demo.Entities.Tag;
import com.example.demo.Repositories.Tag.TagRepository;

import javax.inject.Inject;
import java.util.List;

public class TagService {

    @Inject
    private TagRepository tagRepository;

    public Tag addTag(Tag tag) {
        return this.tagRepository.addTag(tag);
    }
    public List<Tag> allTags() {
        return this.tagRepository.allTags();
    }
    public Tag findTag(Integer id) {
        return this.tagRepository.findTag(id);
    }
    public Tag findTagByName(String name) {
        return this.tagRepository.findTagByName(name);
    }
    public Tag updateTag(Tag tag) {
        return this.tagRepository.updateTag(tag);
    }
    public void deleteTag(Integer id) {
        this.tagRepository.deleteTag(id);
    }
}
