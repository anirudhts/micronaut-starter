package com.project.service;

import com.project.models.Customer;
import com.project.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {


    @Mock
    CustomerRepository customerRepository; //NOPMD

    private CustomerService customerService; //NOPMD


    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void getCustomersShouldReturnAllCustomersFromRepository() {
        List expectedList = mock(List.class);
        when(customerRepository.getAllCustomers()).thenReturn(expectedList);

        List<Customer> actualList = customerService.getCustomers();

        assertEquals(expectedList, actualList);
    }
}