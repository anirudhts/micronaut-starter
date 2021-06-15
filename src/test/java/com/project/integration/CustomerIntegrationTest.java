package com.project.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.models.Customer;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class CustomerIntegrationTest {

  ObjectMapper objectMapper = new ObjectMapper();

  @Inject EmbeddedApplication application;

  @Inject
  @Client("/customer")
  RxHttpClient rxHttpClient;

  @Test
  void testGetCustomersShouldReturnAllCustomers() throws JsonProcessingException {
    String responseString = rxHttpClient.toBlocking().retrieve("/");

    List<Customer> actualCustomers = objectMapper.readValue(responseString, List.class);

    Map<String, String> props1 =
        objectMapper.convertValue(new Customer("abc", "1234567"), Map.class);
    Map<String, String> props2 =
        objectMapper.convertValue(new Customer("def", "765432"), Map.class);
    List<Map<String, String>> expectedCustomers = List.of(props1, props2);

    assertEquals(expectedCustomers, actualCustomers, "All customers should be present");
  }
}
