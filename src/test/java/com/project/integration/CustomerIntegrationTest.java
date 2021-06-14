package com.project.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.models.Customer;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class CustomerIntegrationTest {


    ObjectMapper objectMapper = new ObjectMapper(); //NOPMD

    @Inject
    EmbeddedApplication application;

    @Inject
    @Client("/customer")
    RxHttpClient rxHttpClient; //NOPMD


    @Test
    void testGetCustomersShouldReturnAllCustomers() throws JsonProcessingException {
        String responseString = rxHttpClient.toBlocking().retrieve("/");

        List<Customer> actualCustomers = objectMapper.readValue(responseString, List.class);

        Map<String, String> props1 = objectMapper.convertValue(new Customer("abc", "1234567"), Map.class);
        Map<String, String> props2 = objectMapper.convertValue(new Customer("def", "765432"), Map.class);
        List<Map<String, String>> expectedCustomers = List.of(props1, props2);

        assertEquals(expectedCustomers, actualCustomers);
    }

}
