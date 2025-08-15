package org.example;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.oidc.AccessTokenCredential;
import io.quarkus.security.identity.SecurityIdentity;

import io.quarkus.oidc.Tenant;


// テナントをserviceで指定する場合は、以下コメントアウトを外してください。
// @Tenant("service")
@Path("/mashup")
public class MashupResource {

    @Inject @RestClient ServiceAClient serviceA;
    @Inject @RestClient ServiceBClient serviceB;

    @Inject SecurityIdentity identity;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MashupResult mashup() {
    final String authHeader = bearer();
    try {
        ServiceAResponse a = serviceA.call(authHeader);
        ServiceBResponse b = serviceB.call(authHeader);
        MashupResult r = new MashupResult();
        r.fromServiceA = a; r.fromServiceB = b;
        return r;
    } catch (jakarta.ws.rs.WebApplicationException ex) {
        throw new jakarta.ws.rs.InternalServerErrorException(
        "downstream error: status=" + ex.getResponse().getStatus(), ex);
    } catch (Exception e) {
        throw new jakarta.ws.rs.InternalServerErrorException("downstream call failed: " + e, e);
    }
    }


    private String bearer() {
        var cred = identity.getCredential(AccessTokenCredential.class);
        var token = cred != null ? cred.getToken() : null;
        return token != null ? "Bearer " + token : null;
    }

    public static class ServiceAResponse { public String message; public String detail; }
    // public static class ServiceBResponse { public String message; public int count; }
    public static class ServiceBResponse { public String message; public String detail; }
    public static class MashupResult { public ServiceAResponse fromServiceA; public ServiceBResponse fromServiceB; }
}

