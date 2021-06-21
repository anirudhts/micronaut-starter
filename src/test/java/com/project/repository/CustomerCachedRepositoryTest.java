package com.project.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.project.models.Customer;
import com.project.models.db.CustomerEntity;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
class CustomerCachedRepositoryTest {

  @Inject EmbeddedApplication application;

  @Inject CustomerCachedRepository customerCachedRepository;

  @Test
  void insertShouldAddRecord() {
    CustomerEntity customerEntity = new CustomerEntity(1234L, "ani", "24352");
    customerCachedRepository.insertRecord(customerEntity);
    assertEquals(new Customer(customerEntity), customerCachedRepository.fetchRecord(1234L));
  }

  @Test
  void fetchRecordShouldGetCustomerDetailsById() {
    System.out.println(customerCachedRepository.fetchRecord(1234L));
  }
}
