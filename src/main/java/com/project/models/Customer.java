package com.project.models;

import com.project.models.db.Customers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
  private String name;
  private String phoneNo;

  public Customer(Customers customers) {
    this.name = customers.getName();
    this.phoneNo = customers.getPhoneNo();
  }
}
