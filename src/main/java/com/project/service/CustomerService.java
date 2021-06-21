package com.project.service;

import com.project.models.Account;
import com.project.models.Customer;
import com.project.models.db.AccountEntity;
import com.project.models.db.CustomerEntity;
import com.project.models.dto.out.AccountResponse;
import com.project.repository.AccountRepository;
import com.project.repository.CustomerCachedRepository;
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
  private final CustomerCachedRepository customerCachedRepository;

  @Inject
  public CustomerService(
      CustomerRepository customerRepository,
      AccountRepository accountRepository,
      CustomerCachedRepository customerCachedRepository) {
    this.customerRepository = customerRepository;
    this.accountRepository = accountRepository;
    this.customerCachedRepository = customerCachedRepository;
  }

  public List<Customer> getCustomers() {
    List<CustomerEntity> customerEntities = customerRepository.findAll();

    return customerEntities.stream().map(Customer::new).collect(Collectors.toList());
  }

  public Customer getCustomer(Long customerId) {
    Optional<CustomerEntity> mayBeCustomer = customerRepository.findById(customerId);
    return mayBeCustomer.map(Customer::new).orElseThrow(() -> new IllegalArgumentException());
  }

  public AccountResponse getAllAccountsByCustomerId(Long customerId) {
    List<AccountEntity> accountEntities =
        accountRepository.findByCustomerEntity(
            CustomerEntity.builder().customerId(customerId).build());
    return new AccountResponse(
        accountEntities.stream().map(Account::new).collect(Collectors.toList()));
  }

  public Long insertCustomerToCache(Customer customer) {
    CustomerEntity customerEntity = new CustomerEntity(customer);
    customerCachedRepository.insertRecord(customerEntity);
    return customerEntity.getCustomerId();
  }
}
