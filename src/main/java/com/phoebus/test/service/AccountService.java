package com.phoebus.test.service;

import com.phoebus.test.dao.Account;

import java.util.List;


public interface AccountService {

    public List<Account> retrieveAllAccounts();

    public List<Account> retrieveAllAccounts(Long id);

    public Account createAccount(Account account);
}
