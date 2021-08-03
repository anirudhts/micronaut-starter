package com.project.integration;

import static com.project.constants.AerospikeConstants.NAMESPACE;

import com.project.models.db.CustomerEntity;
import com.project.repository.aerospike.CustomerCachedRepositoryMapperImpl;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.PropertySource;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@MicronautTest
// @MicronautTest(
//    packages = {"com.project.repository.aerospike", "com.project.config"},
//    startApplication = false)
public class AerospikeIntegrationTest {

  @Inject CustomerCachedRepositoryMapperImpl customerCachedRepositoryMapper;

  private static ApplicationContext context;
  //  private AerospikeClient aerospikeClient;
  //  private AeroMapper aeroMapper;

  @Container
  public static GenericContainer aerospike =
      new GenericContainer("aerospike/aerospike-server")
          .withExposedPorts(3000)
          .withEnv("SERVICE_PORT", "3000")
          .withEnv("NAMESPACE", NAMESPACE);

  //  @BeforeEach
  //  void setUp() {
  //
  //    aerospikeClient = new AerospikeClient(aerospike.getHost(),aerospike.getMappedPort(3000));
  //    aeroMapper = new AeroMapper.Builder(aerospikeClient).build();
  //    customerCachedRepositoryMapper = new CustomerCachedRepositoryMapperImpl(aeroMapper);
  //  }

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
  }

  @BeforeEach
  public void initBeansForTest() {
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

    System.out.println(customerEntityFromCache);
  }
}
