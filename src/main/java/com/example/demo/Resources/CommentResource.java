package com.example.demo.Resources;

import com.example.demo.Entities.Comment;
import com.example.demo.Services.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService commentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.commentService.allComments()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Comment create(@Valid Comment comment) {
        return this.commentService.addComment(comment);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment find(@PathParam("id") Integer id) {
        return this.commentService.findComment(id);
    }

    @GET
    @Path("/news/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByNewsId(@PathParam("id") Integer id) {
        return Response.ok(this.commentService.findCommentsByNewsId(id)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment update(@PathParam("id") Integer id, @Valid Comment comment) {
        Comment existingComment = this.commentService.findComment(id);
        if(existingComment != null){
            comment.setId(id);
            return this.commentService.updateComment(comment);
        } else {
            throw new WebApplicationException("Comment with id " + id + " not found", 404);
        }
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        this.commentService.deleteComment(id);
    }
}
