package org.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/a")
public class AResource {
  @GET
  // @RolesAllowed("read")
  @Path("/data")
  @Produces(MediaType.APPLICATION_JSON)
  public AResp get() {
    var r = new AResp();
    r.message = "ok-from-A";
    r.detail = "helloA";
    return r;
  }
  public static class AResp { public String message; public String detail; }
}


