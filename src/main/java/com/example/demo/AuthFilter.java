package com.example.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demo.Entities.Enums.UserType;
import com.example.demo.Resources.UserResource;
import com.example.demo.Services.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    private UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (!this.isAuthRequired(requestContext)) {
            return;
        }

        try {
            String token = requestContext.getHeaderString("Authorization");
            if(token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }

            if (!this.userService.isAuthorized(token) || (!isAdmin(token) && isAdminMethods(requestContext))) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }

        } catch (Exception exception) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }


    private boolean isAuthRequired(ContainerRequestContext req) { //proveravam da li je potrebno izvrsiti aut
        String frontUrl = req.getHeaderString("Frontend-Path"); //sve posle 8081
        System.out.println(frontUrl);
        if (frontUrl == null) return false;
        return frontUrl.equals("/news") || frontUrl.equals("/users") || frontUrl.equals("/categories")
                || frontUrl.equals("/news/add") || frontUrl.equals("/users/add") || frontUrl.equals("/categories/add")
                || frontUrl.matches("/news/\\d+/edit$") || frontUrl.matches("/users/\\d+/edit$")
                || frontUrl.matches("/categories/\\d+/edit$");
    }

    private boolean isAdmin(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String userType = jwt.getClaim("user_type").asString();

        return userType != null && userType.toUpperCase().equals(UserType.ADMIN.toString());
    }

    private boolean isAdminMethods(ContainerRequestContext req) {
        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        String method = req.getMethod().toLowerCase();
        String path = req.getUriInfo().getPath().toLowerCase();
        String regex = "users/\\d+$";

        if (method.equals("delete")) return true;

        for (Object matchedResource : matchedResources) {
            if ((matchedResource instanceof UserResource)) {
                return !method.equals("get") || !path.matches(regex);
            }
        }
        return false;
    }
}
