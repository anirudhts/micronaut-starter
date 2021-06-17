package com.project.integration;

import com.project.repository.AccountRepository;
import com.project.repository.CustomerRepository;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.Optional;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class CustomerAccountRepositoryTest {

  @Inject EmbeddedApplication application;

  @Inject CustomerRepository customerRepository;

  @Inject AccountRepository accountRepository;

  @Test
  void getCustomerById() {
    //        Optional<Customers> maybeCustomer = customerRepository.findByCustomerId(1L);
    //        System.out.println(maybeCustomer.get());
  }

  @Test
  void getCustomerAccountDetails() {
    //        List<Accounts> accounts = customerRepository.findAccountsByCustomerId(1L);
    //        System.out.println(accounts);
  }

  @Test
  void getCustmersByAccountId() {
    Optional<String> maybeCustomers = accountRepository.findCustomersByAccountId(1L);
    System.out.println(maybeCustomers);
  }
}
