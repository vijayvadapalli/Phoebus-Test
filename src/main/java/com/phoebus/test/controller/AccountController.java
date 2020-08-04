package com.phoebus.test.controller;

import com.phoebus.test.model.Account;
import com.phoebus.test.model.Customer;
import com.phoebus.test.exception.CustomerNotFoundException;
import com.phoebus.test.service.AccountService;
import com.phoebus.test.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private CustomerService customerService;

	@GetMapping("/customer/{id}/accounts")
	public List<Account> retrieveAllAccounts(@PathVariable Long id) {
		Customer customer = customerService.retrieveCustomer(id);

		if (customer == null)
			throw new CustomerNotFoundException("Customer Id: " + id);
		
		return customer.getAccounts();
	}


	@PostMapping(path="/customer/{id}/accounts")
	public ResponseEntity<Object> createAccount(@PathVariable Long id, @RequestBody Account account) {

		Customer customer = customerService.retrieveCustomer(id);

		if (customer == null)
			throw new CustomerNotFoundException("Customer Id: " + id);

		account.setCustomer(customer);
		
		accountService.createAccount(account);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(account.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
}
