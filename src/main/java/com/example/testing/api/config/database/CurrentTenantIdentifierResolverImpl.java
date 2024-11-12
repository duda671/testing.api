package com.example.testing.api.config.database;

import java.util.Objects;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver<String> {
  @Override
  public String resolveCurrentTenantIdentifier() {
    String tenant = AppTenantContext.getCurrentTenant();
    return Objects.requireNonNullElse(tenant, "public");
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
