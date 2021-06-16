package com.project.service;

import com.project.models.Customer;
import com.project.models.db.Customers;
import com.project.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    List<Customers> customerEntities = customerRepository.findAll();

    return customerEntities.stream().map(Customer::new).collect(Collectors.toList());
  }

  public Customer getCustomer(Long customerId) {
    Optional<Customers> mayBeCustomer = customerRepository.findById(customerId);
    return mayBeCustomer.map(Customer::new).orElseThrow(() -> new IllegalArgumentException());
  }
}
