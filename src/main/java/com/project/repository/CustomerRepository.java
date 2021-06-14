package com.project.repository;

import com.project.models.Customer;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class CustomerRepository {

    private List<Customer> customers;

    public CustomerRepository() {
        customers = new ArrayList<>();
        customers.add(new Customer("abc", "1234567"));
        customers.add(new Customer("def", "765432"));
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }
}
