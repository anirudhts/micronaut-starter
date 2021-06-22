package com.project.handler;

import com.project.exceptions.ResourceConflictException;
import com.project.models.ErrorResponse;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import javax.inject.Singleton;

@Singleton
@Requires(classes = {ResourceConflictExceptionHandler.class, ExceptionHandler.class})
public class ResourceConflictExceptionHandler
    implements ExceptionHandler<ResourceConflictException, HttpResponse> {
  @Override
  public HttpResponse handle(HttpRequest request, ResourceConflictException exception) {

    ErrorResponse errorResponse = ErrorResponse.builder().message(exception.getMessage()).build();
    return HttpResponse.status(exception.getStatusCode()).body(errorResponse);
  }
}
