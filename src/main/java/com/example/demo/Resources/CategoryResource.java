package com.example.demo.Resources;

import com.example.demo.Entities.Category;
import com.example.demo.Services.CategoryService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/categories")
public class CategoryResource {

    @Inject
    private CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.categoryService.allCategories()).build();
    }

    @GET
    @Path("/page/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response page(@PathParam("page") Integer page) {
        return Response.ok(this.categoryService.pageCategory(page)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Category create(@Valid Category post) {
        return this.categoryService.addCategory(post);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category find(@PathParam("id") Integer id) {
        return this.categoryService.findCategory(id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category update(@PathParam("id") Integer id, @Valid Category category) {
        Category existingCategory = this.categoryService.findCategory(id);
        if(existingCategory != null){
            category.setId(id);
            return this.categoryService.updateCategory(category);
        } else {
            throw new WebApplicationException("Category with id " + id + " not found", 404);
        }
    }


    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        try {
            this.categoryService.deleteCategory(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }


}
