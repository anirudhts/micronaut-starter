package com.project.config;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.WritePolicy;
import io.micronaut.context.annotation.Factory;
import javax.inject.Singleton;

@Factory
public class AerospikeConfig {

  @Singleton
  public AerospikeClient aerospikeClient(AerospikeProperties aerospikeProperties) {
    return new AerospikeClient(aerospikeProperties.getHostname(), aerospikeProperties.getPort());
  }

  @Singleton
  public WritePolicy aerospikeWritePolicy() {
    WritePolicy writePolicy = new WritePolicy();
    writePolicy.setTimeout(10000);
    return writePolicy;
  }
}
