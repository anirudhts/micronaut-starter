package com.project.controller;


import com.project.models.Customer;
import com.project.service.CustomerService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;
import java.util.List;

@Controller("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Inject
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @Get("/")
    public List<Customer> getAllCustomers() {
        return customerService.getCustomers();
    }

}
