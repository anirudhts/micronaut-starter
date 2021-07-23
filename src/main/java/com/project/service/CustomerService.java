package com.project.service;

import com.project.exceptions.ResourceConflictException;
import com.project.exceptions.ResourceNotFoundException;
import com.project.models.Account;
import com.project.models.Customer;
import com.project.models.db.AccountEntity;
import com.project.models.db.CustomerEntity;
import com.project.models.dto.out.AccountResponse;
import com.project.repository.AccountRepository;
import com.project.repository.CustomerRepository;
import com.project.repository.aerospike.CustomerCachedRepository;
import com.project.repository.aerospike.CustomerCachedRepositoryMapperImpl;
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
  private final CustomerCachedRepositoryMapperImpl customerCachedRepositoryMapperImpl;

  @Inject
  public CustomerService(
      CustomerRepository customerRepository,
      AccountRepository accountRepository,
      CustomerCachedRepository customerCachedRepository,
      CustomerCachedRepositoryMapperImpl customerCachedRepositoryMapperImpl) {
    this.customerRepository = customerRepository;
    this.accountRepository = accountRepository;
    this.customerCachedRepository = customerCachedRepository;
    this.customerCachedRepositoryMapperImpl = customerCachedRepositoryMapperImpl;
  }

  public List<Customer> getCustomers() {
    List<CustomerEntity> customerEntities = customerRepository.findAll();

    return customerEntities.stream().map(Customer::new).collect(Collectors.toList());
  }

  public Customer getCustomer(Long customerId) {
    Optional<CustomerEntity> mayBeCustomerEntity =
        customerCachedRepositoryMapperImpl.fetchRecord(customerId);
    return mayBeCustomerEntity
        .map(Customer::new)
        .orElseGet(
            () ->
                customerRepository
                    .findByCustomerId(customerId)
                    .map(
                        customerEntity -> {
                          customerCachedRepositoryMapperImpl.saveRecord(customerEntity);
                          return new Customer(customerEntity);
                        })
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not present")));
  }

  public AccountResponse getAllAccountsByCustomerId(Long customerId) {
    List<AccountEntity> accountEntities =
        accountRepository.findByCustomerEntity(
            CustomerEntity.builder().customerId(customerId).build());
    return new AccountResponse(
        accountEntities.stream().map(Account::new).collect(Collectors.toList()));
  }

  public Customer addCustomer(Customer customer) {
    if (customerRepository.findById(customer.getCustomerId()).isPresent()) {
      throw new ResourceConflictException("Customer is already present.");
    }
    CustomerEntity customerEntity = CustomerEntity.buildCustomerEntityFromCustomer(customer);
    customerRepository.save(customerEntity);
    customerCachedRepository.saveRecord(customerEntity);
    return new Customer(customerEntity);
  }

  public Customer updateCustomer(Long customerId, Customer customer) {
    Optional<CustomerEntity> mayBeCustomerEntity = customerRepository.findById(customerId);
    return mayBeCustomerEntity
        .map(
            customerEntity -> {
              customer.setCustomerId(customerId);
              CustomerEntity updatedCustomerEntity =
                  CustomerEntity.buildCustomerEntityFromCustomer(customer);
              customerRepository.update(updatedCustomerEntity);
              customerCachedRepository.saveRecord(updatedCustomerEntity);
              return new Customer(updatedCustomerEntity);
            })
        .orElseThrow(() -> new ResourceNotFoundException("Customer not present"));
  }

  public void deleteCustomer(Long customerId) {
    Optional<CustomerEntity> mayBeCustomerEntity = customerRepository.findById(customerId);
    mayBeCustomerEntity
        .map(
            customerEntity -> {
              customerRepository.delete(customerEntity);
              customerCachedRepository.deleteRecord(customerId);
              return customerEntity;
            })
        .orElseThrow(() -> new ResourceNotFoundException("Customer not present"));
  }

  public List<Customer> getCustomerByName(String name) {
    List<CustomerEntity> customerEntities = customerRepository.findByName(name);
    return customerEntities.stream().map(Customer::new).collect(Collectors.toList());
  }

  public List<Account> getAccountsForACustomer(Long customerId) {
    List<AccountEntity> accountEntities = accountRepository.findAccountsForCustomerId(customerId);
    return accountEntities.stream().map(Account::new).collect(Collectors.toList());
  }

  public List<Account> getAccountDetailsForACustomer(Long accountId) {
    Optional<AccountEntity> accountEntities = accountRepository.findByAccountId(accountId);
    return accountEntities.stream().map(Account::new).collect(Collectors.toList());
  }

  public List<Account> getMultipleAccountDetails(Long accountId) {
    List<AccountEntity> accountEntities =
        accountRepository.findByAccountIdInRange(accountId, accountId + 10);
    return accountEntities.stream().map(Account::new).collect(Collectors.toList());
  }

  public Customer getCustomerFromDB(Long customerId) {
    return customerRepository
        .findByCustomerId(customerId)
        .map(Customer::new)
        .orElseThrow(() -> new ResourceNotFoundException("Customer not present"));
  }

  public Customer getCustomerFromCache(Long customerId) {
    return customerCachedRepositoryMapperImpl
        .fetchRecord(customerId)
        .map(Customer::new)
        .orElseThrow(() -> new ResourceNotFoundException("Customer not present"));
  }
}
