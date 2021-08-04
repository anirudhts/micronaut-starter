package com.project.integration;

import static com.project.constants.AerospikeConstants.NAMESPACE;

import com.project.models.db.CustomerEntity;
import com.project.repository.aerospike.CustomerCachedRepositoryMapperImpl;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.PropertySource;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.ToStringConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class AerospikeIntegrationTest {

  //  @Inject
  private static CustomerCachedRepositoryMapperImpl customerCachedRepositoryMapper;

  private static ApplicationContext context;

  // @Container
  public static GenericContainer aerospike =
      new GenericContainer("aerospike/aerospike-server")
          .withExposedPorts(3000)
          .withEnv("SERVICE_PORT", "3000")
          .withEnv("NAMESPACE", NAMESPACE)
          .waitingFor(Wait.forLogMessage(".*heartbeat-received.*", 1));
  private static final ToStringConsumer toStringConsumer = new ToStringConsumer();

  @BeforeAll
  static void setup() {
    aerospike.start();
    aerospike.followOutput(toStringConsumer, OutputFrame.OutputType.STDOUT);

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

  @AfterAll
  static void printLogs() {

    aerospike.stop();
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
