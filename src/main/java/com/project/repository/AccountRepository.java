package com.project.repository;

import com.project.models.db.Accounts;
import com.project.models.db.Customers;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.ORACLE)
public interface AccountRepository extends CrudRepository<Accounts, Long> {

  List<Accounts> findByCustomers(Customers customers);

  @Query(
      "SELECT c_.customer_name as name FROM customers c_ INNER JOIN accounts a ON a.customer_id = c_.customer_id where a.account_id = :accountId")
  @Join(value = "customers", alias = "c_")
  Optional<String> findCustomersByAccountId(Long accountId);
}
