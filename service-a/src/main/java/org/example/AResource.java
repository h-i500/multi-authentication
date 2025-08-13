package org.example;

// import jakarta.annotation.security.RolesAllowed;
// import jakarta.ws.rs.Path;
// import jakarta.ws.rs.Produces;
// import jakarta.ws.rs.core.Context;
// import jakarta.ws.rs.core.MediaType;
// import jakarta.ws.rs.core.SecurityContext;
// import jakarta.ws.rs.GET;
// import java.util.Map;

// import org.example.AResource.AResp;

// import io.quarkus.security.identity.SecurityIdentity;
// import jakarta.inject.Inject;

// service-a/src/main/java/.../AResource.java
// @Path("/a/data")
// @Produces(MediaType.APPLICATION_JSON)
// public class AResource {

//   @GET
//   @RolesAllowed("read") // ← Keycloakの service-a クライアントロール read
//   public Map<String, Object> data(@Context SecurityContext ctx) {
//     return Map.of("from", "service-a", "user", ctx.getUserPrincipal().getName());
//   }
// }


// @Path("/a/data")
// @Produces(MediaType.APPLICATION_JSON)
// public class AResource {
//   @GET
//   @RolesAllowed("read")
//   public Map<String,Object> data(@Context SecurityContext ctx) {
//     return Map.of("from","service-a","user",ctx.getUserPrincipal().getName());
//   }
// }


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/a")
public class AResource {
  @GET
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


