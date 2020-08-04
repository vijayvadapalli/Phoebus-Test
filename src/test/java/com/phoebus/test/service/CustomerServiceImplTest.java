package com.phoebus.test.service;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void retrieveAllCustomers_test() {
        List<Customer> customers = getCustomers();

        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> allCustomers= customerService.retrieveAllCustomers();
        assertEquals(2, allCustomers.size());
        verify(customerRepository, times(1)).findAll();
        assert (allCustomers.get(0).getId() == 1);
        assert (allCustomers.get(0).getForeName()).equals("Vijay");
        assert (allCustomers.get(0).getSurName()).equals("Babu");
        assert (allCustomers.get(0).getAccounts().size() == 4);
        assert (allCustomers.get(0).getAccounts().get(0).getAccountNumber() == 10001);
        assert (allCustomers.get(0).getAccounts().get(1).getAccountNumber() == 10002);

        assert (allCustomers.get(1).getId() == 2);
        assert (allCustomers.get(1).getForeName()).equals("Tom");
        assert (allCustomers.get(1).getSurName()).equals("Bailey");
        assert (allCustomers.get(1).getAccounts().size() == 3);
        assert (allCustomers.get(1).getAccounts().get(0).getAccountNumber() == 10005);
        assert (allCustomers.get(1).getAccounts().get(1).getAccountNumber() == 10006);
    }

    @Test
    public void retrieveCustomerById_test() {
        Customer customer = getCustomer(10011, 10012, 10013, 10014);


        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Customer allCustomers= customerService.retrieveCustomer(1L);
        verify(customerRepository, times(1)).findById(1l);
        assert (allCustomers.getId() == 1);
        assert (allCustomers.getForeName()).equals("Vijay");
        assert (allCustomers.getSurName()).equals("Babu");
        assert (allCustomers.getAccounts().size() == 4);
        assert (allCustomers.getAccounts().get(0).getAccountNumber() == 10011);
        assert (allCustomers.getAccounts().get(1).getAccountNumber() == 10012);
        assert (allCustomers.getAccounts().get(2).getAccountNumber() == 10013);
        assert (allCustomers.getAccounts().get(3).getAccountNumber() == 10014);
    }

    @Test
    public void deleteCustomer_test() {
        Customer customer = getCustomer(10011, 10012, 10013, 10014);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        customerService.deleteCustomer(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void createCustomer_test() {
        Customer customer = getCustomer(10011, 10012, 10013, 10014);

        when(customerRepository.save(customer)).thenReturn(customer);
        Customer allCustomers = customerService.createCustomer(customer);
        verify(customerRepository, times(1)).save(customer);

    }

    private Customer getCustomer(int i, int i2, int i3, int i4) {
        Customer customer1 = setUpCustomerData(1, "Vijay", "Babu");
        Account a1 = setUpAccountData(1, i);
        Account a2 = setUpAccountData(2, i2);
        Account a3 = setUpAccountData(3, i3);
        Account a4 = setUpAccountData(4, i4);
        List<Account> accountList1 = new ArrayList<Account>();
        accountList1.add(a1);
        accountList1.add(a2);
        accountList1.add(a3);
        accountList1.add(a4);
        customer1.setAccounts(accountList1);
        return customer1;
    }

    private List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<Customer>();

        Customer customer1 = getCustomer(10001, 10002, 10003, 10004);

        Customer customer2 = setUpCustomerData(2,"Tom", "Bailey");
        Account a5 = setUpAccountData(5,10005);
        Account a6 = setUpAccountData(6,10006);
        Account a7 = setUpAccountData(7,10007);
        List<Account> accountList2 = new ArrayList<Account>();
        accountList2.add(a5);
        accountList2.add(a6);
        accountList2.add(a7);
        customer2.setAccounts(accountList2);

        customers.add(customer1);
        customers.add(customer2);
        return customers;
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

    private Account setUpAccountData(long id, int accountNumber) {

        // given Account
        Account account = new Account();
        account.setId(id);
        account.setAccountNumber(accountNumber);
        return account;
    }
}
