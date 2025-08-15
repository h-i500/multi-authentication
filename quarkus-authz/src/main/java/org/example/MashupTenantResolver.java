package org.example;

import io.quarkus.oidc.TenantResolver;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MashupTenantResolver implements TenantResolver {
  @Override
  public String resolve(RoutingContext ctx) {
    String path = ctx.request().path();
    if (path != null && path.startsWith("/mashup")) {
      String auth = ctx.request().getHeader("Authorization");
      // Bearer が来ていれば service（= 401/WWW-Authenticate ではなく Bearer 検証）
      if (auth != null && auth.startsWith("Bearer ")) {
        return "service";
      }
      // なければ Default（web-app）でコードフロー → ログインにリダイレクト
      return null; // = Default tenant
    }
    return null;
  }
}
