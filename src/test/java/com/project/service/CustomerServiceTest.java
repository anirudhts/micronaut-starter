package com.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.project.models.Customer;
import com.project.models.db.CustomerEntity;
import com.project.repository.AccountRepository;
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
  @Mock AccountRepository accountRepository;

  private CustomerService customerService;

  @BeforeEach
  void setUp() {
    customerService = new CustomerService(customerRepository, accountRepository, null);
  }

  @Test
  void getCustomersShouldReturnAllCustomersFromRepository() {
    List<CustomerEntity> customerEntityFromDb =
        List.of(new CustomerEntity(1234L, "ani", "24352", "", "", "", "", ""));
    List<Customer> expectedCustomers =
        List.of(new Customer(1234L, "ani", "24352", "", "", "", "", ""));

    when(customerRepository.findAll()).thenReturn(customerEntityFromDb);

    List<Customer> actualCustomers = customerService.getCustomers();

    assertEquals(expectedCustomers, actualCustomers, "It retrieves all customers");
  }

  @Test
  void saveCustomers() {
    // customerService.saveCustomers();
  }
}
