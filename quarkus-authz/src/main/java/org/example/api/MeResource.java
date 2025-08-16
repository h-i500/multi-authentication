// // quarkus-authz/src/main/java/example/api/MeResource.java
// package example.api;

// import io.quarkus.security.Authenticated;
// import jakarta.ws.rs.GET;
// import jakarta.ws.rs.Path;
// import jakarta.ws.rs.Produces;
// import org.eclipse.microprofile.jwt.JsonWebToken;
// import jakarta.inject.Inject;
// import java.util.Map;

// @Path("/me")
// @Authenticated
// public class MeResource {
//   @Inject
//   JsonWebToken principal;

//   @GET
//   @Produces("application/json")
//   public Map<String, Object> me() {
//     return Map.of(
//       "name", principal.getName(),
//       "raw-sub", principal.getSubject(),
//       "groups", principal.getGroups()
//     );
//   }
// }


// quarkus-authz/src/main/java/example/api/MeResource.java
package example.api;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.inject.Inject;

import java.util.*;
import java.util.stream.Collectors;

@Path("/me")
@Authenticated
public class MeResource {
  @Inject
  JsonWebToken principal;

  @GET
  @Produces("application/json")
  public Map<String, Object> me() {
    // 1) realm ロール
    Set<String> roles = new LinkedHashSet<>();
    Object realmAccess = principal.getClaim("realm_access");
    if (realmAccess instanceof Map<?, ?> ra) {
      Object rr = ra.get("roles");
      if (rr instanceof Collection<?> col) {
        col.forEach(v -> { if (v != null) roles.add(String.valueOf(v)); });
      }
    }

    // 2) client ロール（resource_access.*.roles を全部集約）
    Object resourceAccess = principal.getClaim("resource_access");
    if (resourceAccess instanceof Map<?, ?> rmap) {
      for (Object v : rmap.values()) {
        if (v instanceof Map<?, ?> client) {
          Object cr = client.get("roles");
          if (cr instanceof Collection<?> col) {
            col.forEach(r -> { if (r != null) roles.add(String.valueOf(r)); });
          }
        }
      }
    }

    // （参考）MP-JWTの groups は Keycloak グループを入れた時だけ出ます
    Set<String> groups = principal.getGroups() != null
        ? principal.getGroups()
        : Collections.emptySet();

    return Map.of(
      "name", principal.getName(),
      "raw-sub", principal.getSubject(),
      "groups", groups,     // 既存のまま残す
      "roles", roles        // ★ 追加（read / user などが入る）
    );
  }
}
