package com.phoebus.test.controller;

import com.phoebus.test.dao.AccountRepository;
import com.phoebus.test.model.Account;
import com.phoebus.test.model.Customer;
import com.phoebus.test.exception.CustomerNotFoundException;
import com.phoebus.test.service.*;
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

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> retrieveAllCustomers() {
        return customerService.retrieveAllCustomers();
    }

    @GetMapping("/customer/{id}")
    public EntityModel<Customer> retrieveCustomer(@PathVariable Long id) {
        Customer customer = customerService.retrieveCustomer(id);

        if (customer == null)
            throw new CustomerNotFoundException("Customer Id: " + id);

        // "all-customers", SERVER_PATH + "/customers"
        // retrieveAllAccounts
        EntityModel<Customer> resource = EntityModel.of(customer);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllAccounts());
        resource.add(linkTo.withRel("all-accounts"));
        // HATEOAS

        return resource;
    }

    protected List<Account> retrieveAllAccounts() {
        return accountRepository.findAll();
    }

    @DeleteMapping(path="/customer/{id}", consumes = "application/json")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

    // input - details of customer
    // output - CREATED & Return the created URI
    // HATEOAS
    @PostMapping(path = "/customer", consumes = "application/json")
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody Customer customer) {
        Customer savedCustomer = customerService.createCustomer(customer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCustomer.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
