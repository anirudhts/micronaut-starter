package com.project;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Micronaut starter project",
            version = "0.0",
            description = "This is a base project for micronaut"))
@SuppressWarnings("PMD.UseUtilityClass")
public class Application {

  public static void main(String[] args) {
    Micronaut.run(Application.class, args);
  }
}
