package org.example;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.example.MashupResource.ServiceBResponse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;

@RegisterRestClient(configKey = "service-b")
@Path("/b")
public interface ServiceBClient {
  @GET @Path("/data")
  @Produces(MediaType.APPLICATION_JSON)
  ServiceBResponse call(@HeaderParam("Authorization") String authorization);
}