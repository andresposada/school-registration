package org.afp.schoolregistration.common.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Component;

@Component
public class FlywayImplementation implements FlywayMigrationStrategy {
  @Override
  public void migrate(Flyway flyway) {
    flyway.migrate();
  }
}
