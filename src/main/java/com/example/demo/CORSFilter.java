package com.example.demo;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        headers.add("Access-Control-Allow-Origin", "http://localhost:8081");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        headers.add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        headers.add("Access-Control-Allow-Credentials", "true");

        if (requestContext.getMethod().equals("OPTIONS")) {
            headers.add("Access-Control-Max-Age", "86400");
            headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization, " + requestContext.getHeaderString("Access-Control-Request-Headers"));
            headers.add("Access-Control-Allow-Credentials", "true");
            responseContext.setStatus(Response.Status.OK.getStatusCode());
        }
    }


}
