package org.example.clients;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "service-b")
@Path("/b")
public interface ServiceBClient {

  @GET
  @Path("/data")
  @Produces(MediaType.APPLICATION_JSON)
  ServiceBResponse call(@HeaderParam("Authorization") String authorization);

  class ServiceBResponse { public String message; public String detail; }
}
