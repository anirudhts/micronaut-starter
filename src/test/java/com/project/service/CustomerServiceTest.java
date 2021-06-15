package com.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.project.models.Customer;
import com.project.repository.CustomerRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  @Mock CustomerRepository customerRepository;

  private CustomerService customerService;

  @BeforeEach
  void setUp() {
    customerService = new CustomerService(customerRepository);
  }

  @Test
  void getCustomersShouldReturnAllCustomersFromRepository() {
    List expectedList = mock(List.class);
    when(customerRepository.getAllCustomers()).thenReturn(expectedList);

    List<Customer> actualList = customerService.getCustomers();

    assertEquals(expectedList, actualList, "It retrieves all customers");
  }
}
