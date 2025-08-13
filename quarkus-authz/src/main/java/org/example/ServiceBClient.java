package org.example;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.Map;

import org.example.MashupResource.ServiceBResponse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;


// // quarkus-authz/src/main/java/.../ServiceBClient.java
// @RegisterRestClient(configKey = "service-b")
// @Path("/b")
// @Produces(MediaType.APPLICATION_JSON)
// public interface ServiceBClient {
//   @GET @Path("/data")
//   Map<String, Object> getData();
// }

@RegisterRestClient(configKey = "service-b")
@Path("/b")
public interface ServiceBClient {
  @GET @Path("/data")
  @Produces(MediaType.APPLICATION_JSON)
  ServiceBResponse call(@HeaderParam("Authorization") String authorization);
}