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
    CustomerEntity customerEntity =
        new CustomerEntity(12345L, "ani", "24352", null, null, null, null, null);
    customerCachedRepository.saveRecord(customerEntity);
    assertEquals(customerEntity, customerCachedRepository.fetchRecord(12345L).get());
  }

  @Test
  void saveRecordShouldUpdateRecordIfAlreadyPresent() {
    long customerId = 12345L;
    CustomerEntity customerEntity =
        new CustomerEntity(12345L, "ani", "24352", null, null, null, null, null);
    customerCachedRepository.saveRecord(customerEntity);

    customerEntity.setPhoneNo("452989");
    customerCachedRepository.saveRecord(customerEntity);
    assertEquals(customerEntity, customerCachedRepository.fetchRecord(customerId).get());
  }

  @Test
  void deleteRecordShouldRemoveTheRecordBasedOnCustomerId() {
    long customerId = 54342L;
    CustomerEntity customerEntity =
        new CustomerEntity(54342L, "ani", "24352", null, null, null, null, null);
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
    CustomerEntity customerEntity =
        new CustomerEntity(12345L, "ani", "24352", null, null, null, null, null);
    customerCachedRepository.putObjectAsBlob(customerEntity);

    assertEquals(customerEntity, customerCachedRepository.getObjectBlob(customerId));
  }

  @Test
  void addSecondaryKeyInSetAndDeleteTheSecondaryKey() {
    //    customerCachedRepository.addSecondaryKey("name", IndexType.STRING);
    //    customerCachedRepository.dropSecondaryKey("name");
  }

  //  @Test
  //  void fetchRecordFromSecondaryKey() {
  //    customerCachedRepository.addSecondaryKey("phone_number", IndexType.STRING);
  //    System.out.println(customerCachedRepository.fetchFromSecondaryKey("phone_number", "24352"));
  //    customerCachedRepository.dropSecondaryKey("phone_number");
  //  }
}
