package com.example.demo.Repositories.News_Tags;

import com.example.demo.Entities.News_Tags;

import java.util.List;

public interface News_TagsRepository {
    News_Tags addNews_Tags(News_Tags news_tags);
    List<News_Tags> allNews_Tags();
    News_Tags findNews_TagsByNewsIdAndTagId(Integer news_id, Integer tag_id);
    List<News_Tags> findNews_TagsByNewsId(Integer news_id);
    List<News_Tags> findNews_TagsByTagId(Integer tag_id);
    News_Tags updateNews_Tags(News_Tags oldNews_Tags, News_Tags newNews_Tags);
    void deleteNews_Tags(Integer news_id, Integer tag_id);
    void deleteNews_TagsByNewsId(Integer news_id);
}
