package com.project.repository;

import com.project.models.db.CustomerEntity;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@JdbcRepository(dialect = Dialect.ORACLE)
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

  @Executable
  @Override
  List<CustomerEntity> findAll();

  @Transactional
  Optional<CustomerEntity> findByCustomerId(@NotNull Long customerId);

  List<CustomerEntity> findByName(String name);
}
