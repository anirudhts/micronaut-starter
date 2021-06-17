package com.project.service;

import com.project.models.Account;
import com.project.models.Customer;
import com.project.models.db.Accounts;
import com.project.models.db.Customers;
import com.project.models.dto.out.AccountResponse;
import com.project.repository.AccountRepository;
import com.project.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final AccountRepository accountRepository;

  @Inject
  public CustomerService(
      CustomerRepository customerRepository, AccountRepository accountRepository) {
    this.customerRepository = customerRepository;
    this.accountRepository = accountRepository;
  }

  public List<Customer> getCustomers() {
    List<Customers> customerEntities = customerRepository.findAll();

    return customerEntities.stream().map(Customer::new).collect(Collectors.toList());
  }

  public Customer getCustomer(Long customerId) {
    Optional<Customers> mayBeCustomer = customerRepository.findById(customerId);
    return mayBeCustomer.map(Customer::new).orElseThrow(() -> new IllegalArgumentException());
  }

  public AccountResponse getAllAccountsByCustomerId(Long customerId) {
    List<Accounts> accountEntities =
        accountRepository.findByCustomers(Customers.builder().customerId(customerId).build());
    return new AccountResponse(
        accountEntities.stream().map(Account::new).collect(Collectors.toList()));
  }
}
