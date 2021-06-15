package com.project.controller;

import com.project.service.HelloWorldService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.inject.Inject;

@Tag(name = "Hello world")
@Controller("/home")
public class HelloWorldController {

  private final HelloWorldService helloWorldService;

  @Inject
  public HelloWorldController(HelloWorldService helloWorldService) {
    this.helloWorldService = helloWorldService;
  }

  @Get("/hello")
  public String helloWorld() {
    return helloWorldService.hello();
  }
}
