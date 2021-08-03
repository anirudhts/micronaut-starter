package com.project.integration;

import static com.project.constants.AerospikeConstants.NAMESPACE;

import com.aerospike.client.AerospikeClient;
import com.aerospike.mapper.tools.AeroMapper;
import com.project.models.db.CustomerEntity;
import com.project.repository.aerospike.CustomerCachedRepositoryMapperImpl;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.PropertySource;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@MicronautTest
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
// @MicronautTest(
//    packages = {"com.project.repository.aerospike", "com.project.config"},
//    startApplication = false)
public class AerospikeIntegrationTest {

  //  @Inject
  CustomerCachedRepositoryMapperImpl customerCachedRepositoryMapper;

  private static ApplicationContext context;
  AerospikeClient aerospikeClient;
  AeroMapper aeroMapper;

  // 172.17.0.1:59826
  // 172.17.0.1:59882
  // 172.17.0.1:59994
  @Container
  public static GenericContainer aerospike =
      new GenericContainer("aerospike/aerospike-server")
          .withExposedPorts(3000)
          .withEnv("SERVICE_PORT", "3000")
          .withEnv("NAMESPACE", NAMESPACE);

  //  @EachBean(AerospikeProperties.class)
  //  void mutateAerospikeProperties(AerospikeProperties aerospikeProperties){
  //    System.out.println("props in test" + aerospikeProperties);
  //  }

  //  @BeforeEach
  //  void setUp() {
  //    System.out.println("Port num: " + aerospike.getMappedPort(3000));
  //    System.out.println("Host IP: " + aerospike.getHost());
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
  public void initTest() {
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
