package com.project.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("aerospike")
public class AerospikeProperties {

  private String hostname;
  private Integer port;
  private String namespace;
}
