package com.project.repository;

import com.project.models.db.AccountEntity;
import com.project.models.db.CustomerEntity;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.ORACLE)
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

  List<AccountEntity> findByCustomerEntity(CustomerEntity customerEntity);

  @Join(value = "customerEntity", type = Join.Type.FETCH)
  Optional<AccountEntity> findByAccountId(Long accountId);

  //  @Query(
  //          value = "select * from accounts  where accounts.account_id = :accountId",
  //          nativeQuery = true)
  //  Optional<AccountEntity> findByAccountIdCustom(Long accountId);

  @Query(
      "SELECT c_.customer_name as name FROM customers c_ INNER JOIN accounts a ON a.customer_id = c_.customer_id where a.account_id = :accountId")
  @Join(value = "customers", alias = "c_")
  Optional<String> findCustomersByAccountId(Long accountId);

  @Query(
      value =
          "select * from accounts inner join customers on customers.customer_id = accounts.customer_id where accounts.customer_id = :customerId",
      nativeQuery = true)
  List<AccountEntity> findAccountsForCustomerId(Long customerId);
}
