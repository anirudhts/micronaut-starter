package com.project.repository;

import com.project.models.db.CustomerEntity;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;

@JdbcRepository(dialect = Dialect.ORACLE)
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

  @Executable
  @Override
  List<CustomerEntity> findAll();

  List<CustomerEntity> findByName(String name);
}
