package com.project.models.db;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedEntity("accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
  @Id
  @MappedProperty("account_id")
  @NotNull
  private Long accountId;

  @MappedProperty("account_type")
  private String accountType;

  @MappedProperty("branch")
  private String branch;

  @ManyToOne(targetEntity = CustomerEntity.class)
  @MappedProperty("customer_id")
  @Nullable
  @JoinColumn(name = "customers", referencedColumnName = "customer_id")
  private CustomerEntity customerEntity;
}
