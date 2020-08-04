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
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.net.URI;
import java.util.List;

@RestController
public class AccountController {

	Logger logger = LogManager.getLogger(AccountController.class);


	@Autowired
	private AccountService accountService;

	@Autowired
	private CustomerService customerService;

	/**
	 * Get all accounts
	 * @param accountId
	 * @return List of Accounts
	 */
	@GetMapping("/customer/{id}/accounts")
	public List<Account> retrieveAllAccounts(@PathVariable Long id) {
		Customer customer = customerService.retrieveCustomer(id);

		if (customer == null) {
			logger.trace("Customer not found for customer id" + id);
			throw new CustomerNotFoundException("Customer Id: " + id);
		}
		
		return customer.getAccounts();
	}

	/**
	 * Create Account for a Customer
	 * @param customerId
	 * @param account
	 * @return account entity object
	 */
	@PostMapping(path="/customer/{id}/accounts")
	public ResponseEntity<Object> createAccount(@PathVariable Long id, @RequestBody Account account) {

		Customer customer = customerService.retrieveCustomer(id);

		if (customer == null){
			logger.trace("Customer not found for customer id" + id);
			throw new CustomerNotFoundException("Customer Id: " + id);
		}

		account.setCustomer(customer);
		
		accountService.createAccount(account);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(account.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}


	/**
	 * Get all accounts
	 *
	 * @return Accounts
	 */
	@GetMapping("/accounts")
	public List<Account> retrieveAllAccounts() {
		logger.trace("Getting all accounts");
		return accountService.retrieveAllAccounts();
	}
}
