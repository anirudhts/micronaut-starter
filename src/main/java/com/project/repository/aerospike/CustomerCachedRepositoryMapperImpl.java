package com.project.repository.aerospike;

import com.aerospike.mapper.tools.AeroMapper;
import com.project.models.db.CustomerEntity;
import io.micronaut.data.annotation.Repository;
import java.util.Optional;

@Repository
public class CustomerCachedRepositoryMapperImpl {

  private final AeroMapper aeroMapper;

  public CustomerCachedRepositoryMapperImpl(AeroMapper aeroMapper) {
    this.aeroMapper = aeroMapper;
  }

  public void saveRecord(CustomerEntity customerEntity) {
    aeroMapper.save(customerEntity);
  }

  public Optional<CustomerEntity> fetchRecord(Long customerId) {
    return Optional.ofNullable(aeroMapper.read(CustomerEntity.class, customerId));
  }
}
