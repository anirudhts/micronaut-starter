package com.project.controller;

import com.project.service.CustomerService;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//Tests using the micronaut way of testing and injects mocks
@MicronautTest
class CustomerControllerTest {

    @Inject
    CustomerService customerService;

    @Inject
    @Client("/customer")
    RxHttpClient rxHttpClient;

    @Test
    void getAllCustomersShouldReturnListOfCustomers() {
        List expectedList = List.of();
        when(customerService.getCustomers()).thenReturn(expectedList);

        List actualList = rxHttpClient.toBlocking().retrieve("/", List.class);
        assertEquals(expectedList, actualList, "The expected response should be same as actual response");
    }

    @MockBean(CustomerService.class)
    CustomerService customerServiceMock() {
        return mock(CustomerService.class);
    }

}