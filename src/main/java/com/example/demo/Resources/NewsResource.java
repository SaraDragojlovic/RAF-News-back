package com.example.demo.Resources;


import com.example.demo.Dto.NewsUpdateDTO;
import com.example.demo.Entities.News;
import com.example.demo.Services.NewsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/news")
public class NewsResource {

    @Inject
    private NewsService newsService;

    @GET
    @Path("/page/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@PathParam("page") Integer page) {
        return Response.ok(this.newsService.allNews(page)).build();
    }

    @GET
    @Path("/category/{categoryId}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allCategoryId(@PathParam("categoryId") Integer categoryId, @PathParam("page") Integer page) {
        return Response.ok(this.newsService.findNewsByCategoryId(categoryId, page)).build();
    }

    @GET
    @Path("/popular")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allCategoryId() {
        return Response.ok(this.newsService.getPopularNews()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public News create(@Valid News news) {
        return this.newsService.addNews(news);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News find(@PathParam("id") Integer id) {
        return this.newsService.findNews(id);
    }

    @GET
    @Path("/search/{search}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@PathParam("search") String search, @PathParam("page") Integer page) {
        return Response.ok(this.newsService.searchNews(search, page)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public News update(@PathParam("id") Integer id, @Valid NewsUpdateDTO newsUpdateDTO) {
        News existingNews = this.newsService.findNews(id);
        if (existingNews != null) {
            existingNews.setTitle(newsUpdateDTO.getTitle());
            existingNews.setText(newsUpdateDTO.getText());
            existingNews.setCategory_id(newsUpdateDTO.getCategory_id());
            return this.newsService.updateNews(existingNews);
        } else {
            throw new WebApplicationException("News with id " + id + " not found", 404);
        }
    }

    @PUT
    @Path("/visit/{news_id}")
    public Response updateVisitCount(@PathParam("news_id") Integer newsId) {
        News existingNews = this.newsService.findNews(newsId);
        if (existingNews != null) {
            this.newsService.updateVisitCount(newsId);
            return Response.ok().build();
        } else {
            throw new WebApplicationException("News with id " + newsId + " not found", 404);
        }
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        this.newsService.deleteNews(id);
    }
}
