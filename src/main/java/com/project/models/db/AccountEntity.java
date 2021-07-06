package com.project.models.db;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;

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

  @MappedProperty("branch_address")
  private String branchAddress;

  @MappedProperty("ifsc_code")
  private String ifscCode;

  @MappedProperty("micr_code")
  private String micrCode;

  @ManyToOne
  @MappedProperty("customer_id")
  @Nullable
  private CustomerEntity customerEntity;
}
