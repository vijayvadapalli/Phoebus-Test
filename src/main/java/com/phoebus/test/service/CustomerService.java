package com.phoebus.test.service;

import com.phoebus.test.model.Customer;

import java.util.List;


public interface CustomerService {

    public List<Customer> retrieveAllCustomers();

    public Customer retrieveCustomer(Long id);

    public void deleteCustomer(Long id);

    public Customer createCustomer(Customer customer);
}
