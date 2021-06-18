package com.project.config;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.WritePolicy;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import javax.inject.Inject;
import javax.inject.Singleton;

@Factory
@Requires(beans = AerospikeProperties.class)
public class AerospikeConfig {

  @Inject private AerospikeProperties aerospikeProperties;

  @Bean
  @Singleton
  @Requires(missingBeans = ObjectMapper.class)
  public ObjectMapper objectMapper() {
    System.out.println("initializing object mapper");
    return new ObjectMapper();
  }

  @Bean
  @Singleton
  @Requires(missingBeans = {AerospikeProperties.class, AerospikeClient.class})
  public AerospikeClient aerospikeClient() {
    System.out.println("initializing aerospike");
    return new AerospikeClient(aerospikeProperties.getHostname(), aerospikeProperties.getPort());
  }

  @Bean
  @Singleton
  public WritePolicy aerospikeWritePolicy() {
    System.out.println("initializing aerospike write policy");
    WritePolicy writePolicy = new WritePolicy();
    writePolicy.setTimeout(10000);
    return writePolicy;
  }
}
