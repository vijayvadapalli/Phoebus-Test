package com.phoebus.test.controller;

import com.phoebus.test.dao.AccountRepository;
import com.phoebus.test.exception.CustomerNotFoundException;
import com.phoebus.test.model.Account;
import com.phoebus.test.model.Customer;
import com.phoebus.test.service.AccountService;
import com.phoebus.test.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CustomerController {

    Logger logger = LogManager.getLogger(CustomerController.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    /**
     * Get all customers
     *
     * @return Customers
     */
    @GetMapping("/customers")
    public List<Customer> retrieveAllCustomers() {
        logger.trace("Getting all customers");
        return customerService.retrieveAllCustomers();
    }

    /**
     * Get customer by ID
     *
     * @param id - customerId
     * @return Customer Entity list
     */
    @GetMapping("/customer/{id}")
    public EntityModel<Customer> retrieveCustomer(@PathVariable Long id) {
        Customer customer = customerService.retrieveCustomer(id);

        if (customer == null) {
            logger.trace("Customer not found for customer id" + id);
            throw new CustomerNotFoundException("Customer Id: " + id);
        }

        // retrieveAllAccounts HATEOAS link
        EntityModel<Customer> customerResource = EntityModel.of(customer);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllAccounts());
        customerResource.add(linkTo.withRel("all-accounts"));

        return customerResource;
    }

    protected List<Account> retrieveAllAccounts() {
        return accountRepository.findAll();
    }


    /**
     * Delete Customer by Id
     *
     * @param id customerId
     */
    @DeleteMapping(path = "/customer/{id}", consumes = "application/json")
    public void deleteCustomer(@PathVariable Long id) {
        logger.info("Deleting Customer with Id" + id);
        customerService.deleteCustomer(id);
    }

    /**
     * Create customer
     *
     * @param customer
     * @return Customer Enitity Object
     */
    @PostMapping(path = "/customer", consumes = "application/json")
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody Customer customer) {
        Customer savedCustomer = customerService.createCustomer(customer);

        logger.info("Created Customer with Id" + savedCustomer.getId());

        // retrieve HATEOAS link
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCustomer.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
