package com.example.demo.Services;

import com.example.demo.Entities.News_Tags;
import com.example.demo.Repositories.News_Tags.News_TagsRepository;

import javax.inject.Inject;
import java.util.List;

public class News_TagsService {

    @Inject
    private News_TagsRepository newsTagsRepository;

    public News_Tags addNews_Tags(News_Tags news_tags) {
        return this.newsTagsRepository.addNews_Tags(news_tags);
    }
    public List<News_Tags> allNews_Tags() {
        return this.newsTagsRepository.allNews_Tags();
    }
    public News_Tags findNews_TagsByNewsIdAndTagId(Integer news_id, Integer tag_id) {
        return this.newsTagsRepository.findNews_TagsByNewsIdAndTagId(news_id, tag_id);
    }
    public List<News_Tags> findNews_TagsByNewsId(Integer news_id) {
        return this.newsTagsRepository.findNews_TagsByNewsId(news_id);
    }
    public List<News_Tags> findNews_TagsByTagId(Integer tag_id) {
        return this.newsTagsRepository.findNews_TagsByTagId(tag_id);
    }
    public News_Tags updateNews_Tags(News_Tags oldNews_Tags, News_Tags newNews_Tags) {
        return this.newsTagsRepository.updateNews_Tags(oldNews_Tags, newNews_Tags);
    }
    public void deleteNews_Tags(Integer news_id, Integer tag_id) {
        this.newsTagsRepository.deleteNews_Tags(news_id, tag_id);
    }
    public void deleteNews_TagsByNewsId(Integer news_id) {
        this.newsTagsRepository.deleteNews_TagsByNewsId(news_id);
    }
}
