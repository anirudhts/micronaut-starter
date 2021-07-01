package com.project.controller;

import com.project.models.Account;
import com.project.models.Customer;
import com.project.models.dto.out.AccountResponse;
import com.project.service.CustomerService;
import io.micronaut.http.HttpResponse;
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
      summary = "Get all customers list",
      description = "Fetches information of all customers")
  @ApiResponse(
      content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
  @ApiResponse(responseCode = "200", description = "Success")
  @ApiResponse(responseCode = "400", description = "Invalid Request")
  @ApiResponse(responseCode = "500", description = "Internal server error")
  public List<Customer> getList() {
    return customerService.getCustomers();
  }

  @Operation(summary = "Get customer", description = "Fetches the customer detail")
  @ApiResponse(
      content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
  @ApiResponse(responseCode = "200", description = "Success")
  @ApiResponse(responseCode = "404", description = "Not found")
  @ApiResponse(responseCode = "500", description = "Internal server error")
  @Get("/{customerId}")
  public Customer get(@PathVariable Long customerId) {
    return customerService.getCustomer(customerId);
  }

  @Operation(
      summary = "Get customer accounts",
      description = "Returns all the accounts for the customer")
  @ApiResponse(
      content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
  @ApiResponse(responseCode = "200", description = "Success")
  @ApiResponse(responseCode = "400", description = "Invalid Request")
  @ApiResponse(responseCode = "500", description = "Internal server error")
  @Get("/{customerId}/accounts")
  public AccountResponse getAllCustomerAccounts(@PathVariable Long customerId) {
    return customerService.getAllAccountsByCustomerId(customerId);
  }

  @Operation(summary = "Create customer", description = "Creates the new customer")
  @ApiResponse(
      content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
  @ApiResponse(responseCode = "200", description = "Success")
  @ApiResponse(responseCode = "422", description = "Validation Error")
  @ApiResponse(responseCode = "500", description = "Internal server error")
  @Post("/")
  public Customer add(@Body Customer customer) {
    return customerService.addCustomer(customer);
  }

  @Operation(summary = "Update customer", description = "Update the customer information")
  @ApiResponse(
      content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
  @ApiResponse(responseCode = "200", description = "Success")
  @ApiResponse(responseCode = "404", description = "Not found")
  @ApiResponse(responseCode = "422", description = "Validation Error")
  @ApiResponse(responseCode = "500", description = "Internal server error")
  @Put("/{customerId}")
  public Customer update(@PathVariable Long customerId, @Body Customer customer) {
    return customerService.updateCustomer(customerId, customer);
  }

  @Operation(summary = "Delete customer", description = "Delete the customer data")
  @ApiResponse(
      content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
  @ApiResponse(responseCode = "204", description = "No Content")
  @ApiResponse(responseCode = "404", description = "Not found")
  @ApiResponse(responseCode = "500", description = "Internal server error")
  @Delete("/{customerId}")
  public HttpResponse delete(@PathVariable Long customerId) {
    customerService.deleteCustomer(customerId);
    return HttpResponse.noContent();
  }

  @Get("/searchByName")
  public List<Customer> getCustomerByName(@QueryValue String name) {
    return customerService.getCustomerByName(name);
  }

  @Get("/accounts/{accountId}")
  public List<Account> getAccountDetailsForACustomer(@PathVariable Long accountId) {
    return customerService.getAccountDetailsForACustomer(accountId);
  }
}
