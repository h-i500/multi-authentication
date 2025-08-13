package org.example;

// import jakarta.annotation.security.RolesAllowed;
// import jakarta.ws.rs.Path;
// import jakarta.ws.rs.Produces;
// import jakarta.ws.rs.core.Context;
// import jakarta.ws.rs.core.MediaType;
// import jakarta.ws.rs.core.SecurityContext;
// import jakarta.ws.rs.GET;
// import java.util.Map;

// service-b/src/main/java/.../BResource.java
// @Path("/b/data")
// @Produces(MediaType.APPLICATION_JSON)
// public class BResource {
//   @GET
//   @RolesAllowed("read")
//   public Map<String, Object> data(@Context SecurityContext ctx) {
//     return Map.of("from", "service-b", "user", ctx.getUserPrincipal().getName());
//   }
// }

// @Path("/b/data")
// @Produces(MediaType.APPLICATION_JSON)
// public class BResource {
//   @GET
//   @RolesAllowed("read")
//   public Map<String,Object> data(@Context SecurityContext ctx) {
//     return Map.of("from","service-b","user",ctx.getUserPrincipal().getName());
//   }
// }


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/b")
public class BResource {
  @GET
  @Path("/data")
  @Produces(MediaType.APPLICATION_JSON)
  public BResp get() {
    var r = new BResp();
    r.message = "ok-from-b";
    r.detail = "helloB";
    return r;
  }
  public static class BResp { public String message; public String detail; }
}
