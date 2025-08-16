// quarkus-authz/src/main/java/example/api/MeResource.java
package example.api;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.inject.Inject;
import java.util.Map;

@Path("/me")
@Authenticated
public class MeResource {
  @Inject
  JsonWebToken principal;

  @GET
  @Produces("application/json")
  public Map<String, Object> me() {
    return Map.of(
      "name", principal.getName(),
      "raw-sub", principal.getSubject(),
      "groups", principal.getGroups()
    );
  }
}