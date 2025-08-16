package org.example.security;

import io.quarkus.oidc.AccessTokenCredential;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
@ApplicationScoped
public class BearerTokenRequestFilter implements ClientRequestFilter {

  @Inject
  SecurityIdentity identity;

  @Override
  public void filter(ClientRequestContext requestContext) {
    AccessTokenCredential cred = identity.getCredential(AccessTokenCredential.class);
    if (cred != null && cred.getToken() != null) {
      requestContext.getHeaders().putSingle("Authorization", "Bearer " + cred.getToken());
    }
  }
}
