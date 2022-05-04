package org.afp.schoolregistration.common.domain;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Domain {
  UUID id;
  Instant createdAt;
  Instant updatedAt;
}
