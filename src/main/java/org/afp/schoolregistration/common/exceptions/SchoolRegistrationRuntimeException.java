package org.afp.schoolregistration.common.exceptions;

public class SchoolRegistrationRuntimeException extends RuntimeException {
  public SchoolRegistrationRuntimeException() {
  }

  public SchoolRegistrationRuntimeException(String message) {
    super(message);
  }

  public SchoolRegistrationRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public SchoolRegistrationRuntimeException(Throwable cause) {
    super(cause);
  }

  public SchoolRegistrationRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
