package com.example.demo.Repositories.News;

import com.example.demo.Entities.News;

import java.util.List;

public interface NewsRepository {

    News addNews(News news);
    List<News> allNews(Integer page);
    News findNews(Integer id);
    List<News> searchNews(String search, Integer page);
    List<News> findNewsByCategoryId(Integer id, Integer page);
    List<News> getPopularNews();
    News updateNews(News news);
    void updateVisitCount(Integer newsId);
    void deleteNews(Integer id);
}
