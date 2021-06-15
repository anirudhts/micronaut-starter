package com.project.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class HelloWorldIntegrationTest {

  @Inject EmbeddedApplication application;

  @Inject
  @Client("/home")
  RxHttpClient rxHttpClient;

  @Test
  void testHelloWorldBlockingWithHttpClient() {
    String helloString = rxHttpClient.toBlocking().retrieve("/hello");
    assertEquals("Hello world", helloString, "Should return Hello World");
  }
}
