package org.example;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.Map;

import org.example.MashupResource.ServiceAResponse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;


// // quarkus-authz/src/main/java/.../ServiceAClient.java
// @RegisterRestClient(configKey = "service-a")
// @Path("/a")
// @Produces(MediaType.APPLICATION_JSON)
// public interface ServiceAClient {
//   @GET @Path("/data")
//   Map<String, Object> getData();
// }

@RegisterRestClient(configKey = "service-a")
@Path("/a")
public interface ServiceAClient {
  @GET @Path("/data")
  @Produces(MediaType.APPLICATION_JSON)
  ServiceAResponse call(@HeaderParam("Authorization") String authorization);
}
