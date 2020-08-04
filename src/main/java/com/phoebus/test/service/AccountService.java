package com.phoebus.test.service;

import com.phoebus.test.model.Account;
import com.phoebus.test.model.Customer;

import java.util.List;


public interface AccountService {

    public List<Account> retrieveAllAccounts();

    public Customer retrieveCustomer(Long id);

    public List<Account> retrieveAllAccounts(Long id);

    public Account createAccount(Account account);
}
