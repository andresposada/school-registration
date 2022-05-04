package org.afp.schoolregistration.common.exceptions;

public class ObjectNotFoundException extends  SchoolRegistrationRuntimeException {
  public ObjectNotFoundException() {
  }

  public ObjectNotFoundException(String message) {
    super(message);
  }
}
