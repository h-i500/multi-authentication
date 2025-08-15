package org.example;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.example.MashupResource.ServiceAResponse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;

@RegisterRestClient(configKey = "service-a")
@Path("/a")
public interface ServiceAClient {
  @GET @Path("/data")
  @Produces(MediaType.APPLICATION_JSON)
  ServiceAResponse call(@HeaderParam("Authorization") String authorization);
}
