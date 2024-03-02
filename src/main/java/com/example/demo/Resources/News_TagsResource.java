package com.example.demo.Resources;

import com.example.demo.Entities.News_Tags;
import com.example.demo.Services.News_TagsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/news_tags")
public class News_TagsResource {

    @Inject
    private News_TagsService newsTagsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.newsTagsService.allNews_Tags()).build();
    }

    @GET
    @Path("/{news_id}/{tag_id}")
    public News_Tags get(@PathParam("news_id") Integer news_id, @PathParam("tag_id") Integer tag_id) {
        return this.newsTagsService.findNews_TagsByNewsIdAndTagId(news_id, tag_id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public News_Tags create(@Valid News_Tags news_tags) {
        return this.newsTagsService.addNews_Tags(news_tags);
    }

    @GET
    @Path("/news/{news_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News_Tags> findNewsTagsByNewsId(@PathParam("news_id") Integer news_id) {
        return this.newsTagsService.findNews_TagsByNewsId(news_id);
    }

    @GET
    @Path("/tag/{tag_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<News_Tags> findNewsTagsByTagId(@PathParam("tag_id") Integer tag_id) {
        return this.newsTagsService.findNews_TagsByTagId(tag_id);
    }

    @PUT
    @Path("/{old_news_id}/{old_tag_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public News_Tags update(@PathParam("old_news_id") Integer old_news_id,
                            @PathParam("old_tag_id") Integer old_tag_id,
                            @Valid News_Tags newNews_Tags) {
        News_Tags oldNews_Tags = this.newsTagsService.findNews_TagsByNewsIdAndTagId(old_news_id, old_tag_id);
        if (oldNews_Tags == null) {
            throw new WebApplicationException("News_Tags with news_id " + old_news_id
                    + " and tag_id " + old_tag_id + " not found", 404);
        }
        return this.newsTagsService.updateNews_Tags(oldNews_Tags, newNews_Tags);
    }

    @DELETE
    @Path("/{news_id}/{tag_id}")
    public void delete(@PathParam("news_id") Integer news_id, @PathParam("tag_id") Integer tag_id) {
        this.newsTagsService.deleteNews_Tags(news_id, tag_id);
    }

    @DELETE
    @Path("/news/{news_id}")
    public void delete(@PathParam("news_id") Integer news_id) {
        this.newsTagsService.deleteNews_TagsByNewsId(news_id);
    }
}
