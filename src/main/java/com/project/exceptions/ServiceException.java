package com.project.exceptions;

import io.micronaut.http.HttpStatus;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
  private static final long serialVersionUID = 100318l;
  private HttpStatus statusCode;
  private String errorMessage;

  public ServiceException(HttpStatus statusCode, String errorMessage) {
    super(errorMessage);
    this.statusCode = statusCode;
    this.errorMessage = errorMessage;
  }
}
