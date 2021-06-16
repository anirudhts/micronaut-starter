package com.project.repository;

import com.project.models.db.Customers;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;

@JdbcRepository(dialect = Dialect.ORACLE)
public interface CustomerRepository extends CrudRepository<Customers, Long> {

  @Executable
  @Override
  List<Customers> findAll();
}
