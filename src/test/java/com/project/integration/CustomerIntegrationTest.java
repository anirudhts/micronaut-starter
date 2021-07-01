package com.project.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import javax.inject.Inject;

@MicronautTest
public class CustomerIntegrationTest {

  ObjectMapper objectMapper = new ObjectMapper();

  @Inject EmbeddedApplication application;

  @Inject
  @Client("/customer")
  RxHttpClient rxHttpClient;

  //  @Test
  //  void testGetCustomersShouldReturnAllCustomers() throws JsonProcessingException {
  //    String responseString = rxHttpClient.toBlocking().retrieve("/");
  //    List<Customer> actualCustomers =
  //        objectMapper.readValue(responseString, new TypeReference<List<Customer>>() {});
  //
  //    List<Customer> expectedCustomers =
  //        List.of(
  //            new Customer(1L, "test123", "1234567890"), new Customer(2L, "testuser",
  // "35465768"));
  //
  //    assertEquals(expectedCustomers, actualCustomers, "All customers should be present");
  //  }
}
