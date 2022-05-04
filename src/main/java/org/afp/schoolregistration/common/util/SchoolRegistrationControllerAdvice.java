package org.afp.schoolregistration.common.util;

import lombok.extern.slf4j.Slf4j;
import org.afp.schoolregistration.common.domain.response.ErrorResponse;
import org.afp.schoolregistration.common.exceptions.BusinessValidationException;
import org.afp.schoolregistration.common.exceptions.DuplicatedEntryException;
import org.afp.schoolregistration.common.exceptions.ObjectNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class SchoolRegistrationControllerAdvice {

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Mono<ResponseEntity<ErrorResponse>> handleUncaughtException(Throwable t) {
    return buildErrorResponse(t, t.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(WebExchangeBindException.class)
  @ResponseStatus(PRECONDITION_FAILED)
  public Mono<ResponseEntity<ErrorResponse>> invalidRequestErrorHandler(
          @NotNull final WebExchangeBindException e) {

    var errors =
            e.getBindingResult().getAllErrors().stream()
                    .filter(Objects::nonNull)
                    .map(this::getValidationErrorMessage)
                    .collect(Collectors.toList());
    return buildErrorResponse(e, String.join(" | ", errors), PRECONDITION_FAILED);
  }

  @NotNull
  private String getValidationErrorMessage(@NotNull final ObjectError error) {
    final var errorMessage = new StringBuilder();
    if (error instanceof FieldError) {
      var fe = (FieldError) error;
      errorMessage.append(fe.getField()).append(" - ");
    }
    errorMessage.append(error.getDefaultMessage());
    return errorMessage.toString();
  }

  @ExceptionHandler(DuplicatedEntryException.class)
  @ResponseStatus(PRECONDITION_FAILED)
  public Mono<ResponseEntity<ErrorResponse>> handleDuplicatedEntry(DuplicatedEntryException duplicatedEntryException) {
    return buildErrorResponse(duplicatedEntryException, duplicatedEntryException.getMessage(), PRECONDITION_FAILED);
  }

  @ExceptionHandler(ObjectNotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public Mono<ResponseEntity<ErrorResponse>> handleObjectNotFoundException(ObjectNotFoundException exception) {
    return buildErrorResponse(exception, exception.getMessage(), NOT_FOUND);
  }

  @ExceptionHandler(BusinessValidationException.class)
  @ResponseStatus(PRECONDITION_FAILED)
  public Mono<ResponseEntity<ErrorResponse>> handleBusinessException(BusinessValidationException exception) {
    return buildErrorResponse(exception, exception.getMessage(), PRECONDITION_FAILED);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(CONFLICT)
  public Mono<ResponseEntity<ErrorResponse>> handleDataIntegrityException(DataIntegrityViolationException exception) {
    return buildErrorResponse(exception, "Could not delete the requested domain, constraint violation has been triggered by the DB", CONFLICT);
  }



  /**
   * Builds the {@code ErrorResponse} object to serve all error request and response generic message
   *
   * @param e          Exception thrown by the handler itself
   * @param message    Message to be shown in the consumer request
   * @param httpStatus HTTP status to be sent it to the consumer
   * @return ErrorRepose
   */
  private Mono<ResponseEntity<ErrorResponse>> buildErrorResponse(
          Throwable e, String message, HttpStatus httpStatus) {
    log.error(ExceptionUtils.getStackTrace(e));
    var body = new ErrorResponse(message, httpStatus.value());
    return Mono.just(ResponseEntity.status(httpStatus).body(body));
  }
}
