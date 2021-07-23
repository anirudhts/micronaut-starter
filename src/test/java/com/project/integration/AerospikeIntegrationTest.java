package com.project.integration;

import static com.project.constants.AerospikeConstants.NAMESPACE;

import com.project.models.db.CustomerEntity;
import com.project.repository.aerospike.CustomerCachedRepositoryMapperImpl;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@MicronautTest(
    packages = {"com.project.repository.aerospike", "com.project.config"},
    startApplication = false)
public class AerospikeIntegrationTest {

  //    @Inject
  CustomerCachedRepositoryMapperImpl customerCachedRepositoryMapper;

  // 172.17.0.1:59826
  // 172.17.0.1:59882
  // 172.17.0.1:59994
  @Container
  public static GenericContainer aerospike =
      new GenericContainer("aerospike/aerospike-server")
          .withExposedPorts(3000)
          .withEnv("SERVICE_PORT", "3000")
          .withEnv("NAMESPACE", NAMESPACE);

  @BeforeEach
  void setUp() {
    System.out.println("Port num: " + aerospike.getMappedPort(3000));
    System.out.println("Host IP: " + aerospike.getHost());

    //   AerospikeClient aerospikeClient = new
    // AerospikeClient(aerospike.getHost(),aerospike.getMappedPort(3000));
  }

  @Test
  void saveRecord() {
    long customerId = 1234L;
    CustomerEntity customerEntity =
        new CustomerEntity(
            customerId,
            "ani",
            "24352",
            "someValue",
            "someValue",
            "someValue",
            "someValue",
            "someValue");
    customerCachedRepositoryMapper.saveRecord(customerEntity);

    Optional<CustomerEntity> customerEntityFromCache =
        customerCachedRepositoryMapper.fetchRecord(customerId);

    System.out.println(customerEntityFromCache);
  }
}
