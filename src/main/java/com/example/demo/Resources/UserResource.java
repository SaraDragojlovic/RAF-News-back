package com.example.demo.Resources;

import com.example.demo.Dto.LoginRequest;
import com.example.demo.Dto.UserUpdateDTO;
import com.example.demo.Entities.User;
import com.example.demo.Services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.userService.allUsers()).build();
    }

    @GET
    @Path("/page/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response page(@PathParam("page") Integer page) {
        return Response.ok(this.userService.pageUsers(page)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User create(@Valid User user) {
        return this.userService.addUser(user);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User find(@PathParam("id") Integer id) {
        return this.userService.findUser(id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User update(@PathParam("id") Integer id, @Valid UserUpdateDTO userDTO) {
        //primim fto i pronadjem usera preko id i ako postoji mu setujem i dam mu tog vec promenjenog usera
        User existingUser = this.userService.findUser(id);
        if (existingUser != null) {
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setFirst_name(userDTO.getFirst_name());
            existingUser.setLast_name(userDTO.getLast_name());
            existingUser.setUser_type(userDTO.getUser_type());
            return this.userService.updateUser(existingUser);
        } else {
            throw new WebApplicationException("User with id " + id + " not found", 404);
        }
    }

    @PUT
    @Path("/{id}/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStatus(@PathParam("id") Integer id,@PathParam("status") String status) {
        try {
            this.userService.updateStatus(id, status);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        this.userService.deleteUser(id);
    }

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest) {
        Map<String, String> response = new HashMap<>();

        String jwt = this.userService.login(loginRequest.getEmail(), loginRequest.getPassword()); //vraca jwt
        if (jwt == null) {
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);

        return Response.ok(response).build();
    }
}
