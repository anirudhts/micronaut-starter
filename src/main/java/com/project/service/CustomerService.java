package com.project.service;

import com.project.models.Customer;
import com.project.repository.CustomerRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class CustomerService {

    private final CustomerRepository customerRepository; //NOPMD

    @Inject
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.getAllCustomers();
    }
}
