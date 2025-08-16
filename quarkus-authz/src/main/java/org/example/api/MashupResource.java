package org.example.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.oidc.AccessTokenCredential;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;

import org.example.clients.ServiceAClient;
import org.example.clients.ServiceBClient;

@Path("/mashup")
@Authenticated
public class MashupResource {

  @Inject @RestClient ServiceAClient serviceA;
  @Inject @RestClient ServiceBClient serviceB;
  @Inject SecurityIdentity identity;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response mashup() {
    final String authHeader = bearer();
    try {
      var a = serviceA.call(authHeader);
      var b = serviceB.call(authHeader);
      var r = new MashupResult();
      r.fromServiceA = a;
      r.fromServiceB = b;
      return Response.ok(r).build();

    } catch (jakarta.ws.rs.WebApplicationException ex) {
      final int st = ex.getResponse().getStatus();
      if (st == 401 || st == 403) {
        // 認証/認可エラーは 403 に正規化して返す（フロントで「権限エラー」表示）
        return Response.status(Response.Status.FORBIDDEN)
            .entity(new ErrorBody("forbidden", "権限エラー"))
            .build();
      }
      // それ以外は 500
      throw new jakarta.ws.rs.InternalServerErrorException(
          "downstream error: status=" + st, ex);
    } catch (Exception e) {
      throw new jakarta.ws.rs.InternalServerErrorException("downstream call failed: " + e, e);
    }
  }

  private String bearer() {
    var cred = identity.getCredential(AccessTokenCredential.class);
    var token = cred != null ? cred.getToken() : null;
    return token != null ? "Bearer " + token : null;
  }

  public static class MashupResult {
    public ServiceAClient.ServiceAResponse fromServiceA;
    public ServiceBClient.ServiceBResponse fromServiceB;
  }
  public static class ErrorBody {
    public String error; public String message;
    public ErrorBody(String e, String m) { this.error=e; this.message=m; }
  }
}
