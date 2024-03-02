package com.example.demo.Resources;

import com.example.demo.Entities.Tag;
import com.example.demo.Services.TagService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tags")
public class TagResource {

    @Inject
    private TagService tagService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.tagService.allTags()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Tag create(@Valid Tag tag) {
        return this.tagService.addTag(tag);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tag find(@PathParam("id") Integer id) {
        return this.tagService.findTag(id);
    }

    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tag findByName(@PathParam("name") String name) {
        return this.tagService.findTagByName(name);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tag update(@PathParam("id") Integer id, @Valid Tag tag) {
        Tag existingTag = this.tagService.findTag(id);
        if (existingTag != null) {
            tag.setId(id);
            return this.tagService.updateTag(tag);
        } else {
            throw new WebApplicationException("Tag with id " + id + " not found", 404);
        }
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        this.tagService.deleteTag(id);
    }
}
