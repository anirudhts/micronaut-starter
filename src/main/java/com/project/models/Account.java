package com.project.models;

import com.project.models.db.Accounts;
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

  public Account(Accounts accounts) {
    this.accountId = accounts.getAccountId();
    this.accountType = accounts.getAccountType();
    this.branch = accounts.getBranch();
  }
}
