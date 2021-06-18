package com.project.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.project.config.AerospikeConfig;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
class CustomerCachedRepositoryTest {

  @Inject EmbeddedApplication application;

  @Inject CustomerCachedRepository customerCachedRepository;

  @Inject AerospikeConfig aerospikeConfig;

  @Test
  void insertShouldAddRecord() {
    customerCachedRepository.insertRecord();
  }
}
