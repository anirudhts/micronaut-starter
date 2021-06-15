package com.project.service;

import com.project.models.Customer;
import com.project.repository.CustomerRepository;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomerService {

  private final CustomerRepository customerRepository;

  @Inject
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> getCustomers() {
    return customerRepository.getAllCustomers();
  }
}
