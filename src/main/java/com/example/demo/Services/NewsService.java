package com.example.demo.Services;

import com.example.demo.Entities.News;
import com.example.demo.Repositories.News.NewsRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsService {

    @Inject
    private NewsRepository newsRepository;

    public News addNews(News news) {
        return this.newsRepository.addNews(news);
    }
    public List<News> allNews(Integer page) {
        return this.newsRepository.allNews(page);
    }
    public News findNews(Integer id) {
        return this.newsRepository.findNews(id);
    }
    public List<News> searchNews(String search, Integer page) {
        return this.newsRepository.searchNews(search, page);
    }
    public List<News> findNewsByCategoryId(Integer id, Integer page) {
        return this.newsRepository.findNewsByCategoryId(id, page);
    }
    public List<News> getPopularNews() {
        return this.newsRepository.getPopularNews();
    }
    public News updateNews(News news) {
        return this.newsRepository.updateNews(news);
    }
    public void updateVisitCount(Integer newsId){
        this.newsRepository.updateVisitCount(newsId);
    }
    public void deleteNews(Integer id) {
        this.newsRepository.deleteNews(id);
    }
}
