package com.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import javax.inject.Singleton;

@Factory
public class ObjectMapperConfig {

  @Bean
  @Singleton
  public ObjectMapper objectMapper() {
    System.out.println("initializing object mapper");
    return new ObjectMapper();
  }
}
