package com.project.integration;

import static com.project.constants.AerospikeConstants.NAMESPACE;

import com.project.models.db.CustomerEntity;
import com.project.repository.aerospike.CustomerCachedRepositoryMapperImpl;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.PropertySource;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
// @MicronautTest
// @MicronautTest(
//    packages = {"com.project.repository.aerospike", "com.project.config"},
//    startApplication = false)
public class AerospikeIntegrationTest {

  //  @Inject
  private static CustomerCachedRepositoryMapperImpl customerCachedRepositoryMapper;

  private static ApplicationContext context;

  @Container
  public static GenericContainer aerospike =
      new GenericContainer("aerospike/aerospike-server")
          .withExposedPorts(3000)
          .withEnv("SERVICE_PORT", "3000")
          .withEnv("NAMESPACE", NAMESPACE)
          .withStartupTimeout(Duration.ofSeconds(15));

  @BeforeAll
  static void setup() {
    context =
        ApplicationContext.run(
            PropertySource.of(
                "test",
                Map.of(
                    "aerospike.hostname",
                    aerospike.getHost(),
                    "aerospike.port",
                    aerospike.getMappedPort(3000))));

    customerCachedRepositoryMapper = context.getBean(CustomerCachedRepositoryMapperImpl.class);
  }

  @Test
  void saveRecord() {
    long customerId = 1235L;
    CustomerEntity customerEntity =
        new CustomerEntity(
            customerId,
            "gugan",
            "24352",
            "someValue",
            "someValue",
            "someValue",
            "someValue",
            "someValue");
    customerCachedRepositoryMapper.saveRecord(customerEntity);

    Optional<CustomerEntity> customerEntityFromCache =
        customerCachedRepositoryMapper.fetchRecord(customerId);

    Assertions.assertEquals(customerEntity, customerEntityFromCache.get());
  }
}
