package com.project.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.project.models.db.CustomerEntity;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.Optional;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
class CustomerCachedRepositoryMapperImplTest {

  @Inject EmbeddedApplication application;

  @Inject CustomerCachedRepositoryMapperImpl customerCachedRepositoryMapper;

  @Test
  void saveRecordUsingMapperShouldAddANewRecord() {
    CustomerEntity customerEntity = new CustomerEntity(1234L, "ani", "24352", "", "", "", "", "");
    customerCachedRepositoryMapper.saveRecord(customerEntity);
    Optional<CustomerEntity> actualCustomer = customerCachedRepositoryMapper.fetchRecord(1234L);
    assertEquals(customerEntity, actualCustomer.get());
  }

  @Test
  void fetchRecordShouldReturnEmptyOptionalIfRecordIsNotPresent() {
    assertEquals(
        customerCachedRepositoryMapper.fetchRecord(System.currentTimeMillis()), Optional.empty());
  }
}
