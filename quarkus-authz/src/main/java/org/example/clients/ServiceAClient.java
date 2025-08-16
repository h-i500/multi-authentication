package org.example.clients;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "service-a")
@Path("/a")
public interface ServiceAClient {

  @GET
  @Path("/data")
  @Produces(MediaType.APPLICATION_JSON)
  ServiceAResponse call(@HeaderParam("Authorization") String authorization);

  class ServiceAResponse { public String message; public String detail; }
}
