package org.afp.schoolregistration.common.exceptions;

public class BusinessValidationException extends SchoolRegistrationRuntimeException {

  public BusinessValidationException() {
  }

  public BusinessValidationException(String message) {
    super(message);
  }
}
