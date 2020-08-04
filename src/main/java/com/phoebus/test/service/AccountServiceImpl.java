package com.phoebus.test.service;

import com.phoebus.test.dao.Account;
import com.phoebus.test.dao.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> retrieveAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> retrieveAllAccounts(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        if (customerOptional.isPresent())
            return customerOptional.get().getAccounts();
        else
            return null;

    }


    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
}
