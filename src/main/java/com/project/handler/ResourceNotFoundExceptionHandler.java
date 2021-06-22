package com.project.handler;

import com.project.exceptions.ResourceNotFoundException;
import com.project.models.ErrorResponse;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import javax.inject.Singleton;

@Singleton
@Requires(classes = {ResourceNotFoundExceptionHandler.class, ExceptionHandler.class})
public class ResourceNotFoundExceptionHandler
    implements ExceptionHandler<ResourceNotFoundException, HttpResponse> {
  @Override
  public HttpResponse handle(HttpRequest request, ResourceNotFoundException exception) {

    ErrorResponse errorResponse = ErrorResponse.builder().message(exception.getMessage()).build();
    return HttpResponse.status(exception.getStatusCode()).body(errorResponse);
  }
}
