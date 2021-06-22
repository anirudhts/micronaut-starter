package com.project.exceptions;

import io.micronaut.http.HttpStatus;

public class ResourceNotFoundException extends ServiceException {
  private static final long serialVersionUID = 100318l;

  public ResourceNotFoundException(String errorMessage) {
    super(HttpStatus.NOT_FOUND, errorMessage);
  }
}
