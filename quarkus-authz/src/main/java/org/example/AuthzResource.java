package org.example;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

@Path("/authz")
public class AuthzResource {

    @POST
    @Path("/check")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response check(AuthzRequest request) {
        if ("allowed".equals(request.getUser())) {
            return Response.ok(Map.of("authorized", true)).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
}
