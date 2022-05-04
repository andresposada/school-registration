package org.afp.schoolregistration.common.exceptions;

public class DuplicatedEntryException extends SchoolRegistrationRuntimeException {

  public DuplicatedEntryException() {
  }

  public DuplicatedEntryException(String message) {
    super(message);
  }
}
