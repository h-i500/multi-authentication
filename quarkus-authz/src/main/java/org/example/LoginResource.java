// src/main/java/org/example/LoginResource.java
package org.example;

import java.net.URI;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import io.quarkus.oidc.OidcSession;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;

@Path("/")
public class LoginResource {
  @Inject SecurityIdentity identity;

  @GET
  @Path("login")
  @PermitAll
  public Response login() {
    // 未ログインなら /secure へ誘導して OIDC を開始
    if (identity == null || identity.isAnonymous()) {
      return Response.seeOther(URI.create("/secure")).build(); // ゲートウェイ経由では /api/secure
    }
    // ログイン済みならフロントへ
    return Response.seeOther(URI.create("/app/")).build();
  }

  @GET
  @Path("secure")
  @Authenticated
  public Response secured() {
    return Response.seeOther(URI.create("/app/")).build();
  }

  @GET @Path("/logout")
  public Response logout(@Context SecurityIdentity id, @Context OidcSession session) {
    session.logout().await().indefinitely();
    return Response.seeOther(URI.create("/app/")).build(); // ← ここを好みの戻り先に
  }
  
}

