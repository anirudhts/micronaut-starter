package com.project.models;

import com.project.models.db.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
  private Long accountId;
  private String accountType;
  private String branch;
  private Customer customer;

  public Account(AccountEntity accountEntity) {
    this.accountId = accountEntity.getAccountId();
    this.accountType = accountEntity.getAccountType();
    this.branch = accountEntity.getBranch();
    this.customer = new Customer(accountEntity.getCustomerEntity());
  }
}
