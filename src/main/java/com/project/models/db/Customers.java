package com.project.models.db;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedEntity("customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customers {
  @Id
  @MappedProperty("customer_id")
  @NotNull
  private Long customerId;

  @MappedProperty("customer_name")
  private String name;

  @MappedProperty("phone_number")
  private String phoneNo;

  //  @OneToMany(targetEntity = Accounts.class)
  //  @JoinColumn(name = "accounts", referencedColumnName = "customer_id")
  //  @Nullable
  //  private List<Accounts> accounts;
}
