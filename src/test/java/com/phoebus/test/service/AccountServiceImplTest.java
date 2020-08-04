package com.phoebus.test.service;

import com.phoebus.test.dao.AccountRepository;
import com.phoebus.test.dao.CustomerRepository;
import com.phoebus.test.model.Account;
import com.phoebus.test.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {
    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void retrieveAllAccounts_test() {
        List<Account> accounts = getAccounts();

        when(accountRepository.findAll()).thenReturn(accounts);
        List<Account> accountsReturned = accountService.retrieveAllAccounts();
        assertEquals(3, accountsReturned.size());
        verify(accountRepository, times(1)).findAll();
        assert (accounts.get(0).getId() == 1);
        assert (accounts.get(0).getAccountNumber() == 10001);
        assert (accounts.get(0).getCustomer().getForeName()).equals("Vijay");
        assert (accounts.get(0).getCustomer().getSurName()).equals("Babu");
    }

    @Test
    public void retrieveAccountById_test() {
        Customer customer = setUpCustomerData(1,"Vijay", "Babu");
        customer.setAccounts(getAccounts());
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        List<Account> accountsReturned = accountService.retrieveAllAccounts(1l);
        assertEquals(3, accountsReturned.size());
        verify(customerRepository, times(1)).findById(1l);
        assert (customer.getId() == 1);
        assert (customer.getAccounts().size() == 3);
        assert (customer.getForeName()).equals("Vijay");
        assert (customer.getSurName()).equals("Babu");
    }

    @Test
    public void saveAccount_test() {
        Account account = setUpAccountData(3,"Rich", "Amuzu", 10003);
        when(accountRepository.save(account)).thenReturn(account);
        accountService.createAccount(account);
        verify(accountRepository, times(1)).save(account);
    }

    private List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<Account>();
        accounts.add(setUpAccountData(1,"Vijay", "Babu", 10001));
        accounts.add(setUpAccountData(2,"Tom", "Hanks", 10002));
        accounts.add(setUpAccountData(3,"Rich", "Amuzu", 10003));

        return accounts;
    }

    private Customer setUpCustomerData(long id, String fname, String lName) {
        Date localDate = new Date();
        // given customer
        Customer customer = new Customer();
        customer.setId(id);
        customer.setForeName(fname);
        customer.setSurName(lName);
        customer.setBirthDate(localDate);

        return customer;
    }

    private Account setUpAccountData(long id, String fname, String lName, int accountNumber) {

        Customer customer = setUpCustomerData(id, fname, lName);
        // given Account
        Account account = new Account();
        account.setId(id);
        account.setAccountNumber(accountNumber);
        account.setCustomer(customer);
        return account;
    }
}
