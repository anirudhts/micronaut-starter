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
  private String micrCode;
  private String ifscCode;
  private String branchAddress;
  private Customer customer;

  public Account(AccountEntity accountEntity) {
    this.accountId = accountEntity.getAccountId();
    this.accountType = accountEntity.getAccountType();
    this.branch = accountEntity.getBranch();
    this.ifscCode = accountEntity.getIfscCode();
    this.micrCode = accountEntity.getMicrCode();
    this.branchAddress = accountEntity.getBranchAddress();
    this.customer = new Customer(accountEntity.getCustomerEntity());
  }
}
