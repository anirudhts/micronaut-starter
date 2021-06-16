package com.project.models.db;

import com.project.models.Customer;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@MappedEntity("customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customers {
  @Id
  @MappedProperty("customer_id")
  @NotNull
  private Long customerId;

  @MappedProperty("customer_name")
  private String name;

  @MappedProperty("phone_number")
  private String phoneNo;
}
