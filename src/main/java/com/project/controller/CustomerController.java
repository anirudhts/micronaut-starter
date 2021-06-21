package com.project.controller;

import com.project.models.Customer;
import com.project.models.dto.out.AccountResponse;
import com.project.service.CustomerService;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.inject.Inject;

@Tag(name = "Customer controller")
@Controller("/customer")
public class CustomerController {

  private final CustomerService customerService;

  @Inject
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Get("/")
  @Operation(
      summary = "Get customers",
      description = "Fetches information of all customers of the system")
  @ApiResponse(
      content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
  @ApiResponse(responseCode = "200", description = "Success")
  @ApiResponse(responseCode = "400", description = "Invalid Request")
  @ApiResponse(responseCode = "404", description = "Not found")
  @ApiResponse(responseCode = "500", description = "Internal server error")
  public List<Customer> getAllCustomers() {
    return customerService.getCustomers();
  }

  @Get("/{customerId}")
  public Customer getCustomer(@PathVariable Long customerId) {
    return customerService.getCustomer(customerId);
  }

  @Get("/{customerId}/accounts")
  public AccountResponse getAllCustomerAccounts(@PathVariable Long customerId) {
    return customerService.getAllAccountsByCustomerId(customerId);
  }

  @Post("/")
  public Long addNewCustomer(@Body Customer customer) {
    return customerService.insertCustomerToCache(customer);
  }
}
