package com.project.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.project.models.db.CustomerEntity;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.Optional;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
class CustomerCachedRepositoryTest {

  @Inject EmbeddedApplication application;

  @Inject CustomerCachedRepository customerCachedRepository;

  @Test
  void saveRecordShouldAddANewRecord() {
    CustomerEntity customerEntity = new CustomerEntity(1234L, "ani", "24352");
    customerCachedRepository.saveRecord(customerEntity);
    assertEquals(customerEntity, customerCachedRepository.fetchRecord(1234L).get());
  }

  @Test
  void saveRecordShouldUpdateRecordIfAlreadyPresent() {
    long customerId = 12345L;
    CustomerEntity customerEntity = new CustomerEntity(customerId, "atul", "34234");
    customerCachedRepository.saveRecord(customerEntity);

    customerEntity.setPhoneNo("452989");
    customerCachedRepository.saveRecord(customerEntity);
    assertEquals(customerEntity, customerCachedRepository.fetchRecord(customerId).get());
  }

  @Test
  void deleteRecordShouldRemoveTheRecordBasedOnCustomerId() {
    long customerId = 54342L;
    CustomerEntity customerEntity = new CustomerEntity(customerId, "customerToDelete", "24352");
    customerCachedRepository.saveRecord(customerEntity);
    customerCachedRepository.deleteRecord(customerId);

    assertEquals(Optional.empty(), customerCachedRepository.fetchRecord(customerId));
  }

  @Test
  void fetchRecordShouldReturnEmptyOptionalIfRecordIsNotPresent() {
    assertEquals(
        customerCachedRepository.fetchRecord(System.currentTimeMillis()), Optional.empty());
  }

  @Test
  void putObjectShouldPersistEntireObject() {
    long customerId = 12345L;
    CustomerEntity customerEntity = new CustomerEntity(customerId, "who", "24352");
    customerCachedRepository.putObjectAsBlob(customerEntity);

    assertEquals(customerEntity, customerCachedRepository.getObjectBlob(customerId));
  }
}
