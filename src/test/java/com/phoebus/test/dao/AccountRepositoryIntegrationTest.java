package com.phoebus.test.dao;

import com.phoebus.test.dao.AccountRepository;
import com.phoebus.test.dao.CustomerRepository;
import com.phoebus.test.model.Account;
import com.phoebus.test.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AccountRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void whenFindById_thenReturnAccount() {

        Customer customer = setUpCustomerData();

        // when
        Optional<Account> found = accountRepository.findById(1l);

        // then
        assert(found.get().getAccountNumber() == 10001);
        assert(found.get().getCustomer().getForeName()).equals(customer.getForeName());
        assert(found.get().getCustomer().getSurName()).equals(customer.getSurName());
        assert(found.get().getCustomer().getBirthDate()).equals(customer.getBirthDate());
    }

    private Customer setUpCustomerData() {
        Date localDate = new Date();
        // given customer
        Customer customer = new Customer();
        customer.setForeName("Vijay");
        customer.setSurName("Babu");
        customer.setBirthDate(localDate);

        // given Accounts
        Account account = new Account();
        account.setAccountNumber(10001);
        account.setCustomer(customer);

        Account account2 = new Account();
        account2.setAccountNumber(10002);
        account2.setCustomer(customer);

        List<Account> accountList = new ArrayList<Account>();
        accountList.add(account);
        accountList.add(account2);
        customer.setAccounts(accountList);

        entityManager.persist(account);
        entityManager.persist(account2);
        entityManager.persist(customer);
        entityManager.flush();

        return customer;
    }
}
