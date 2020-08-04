package com.phoebus.test.service;

import com.phoebus.test.dao.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    // write test cases here

    @Test
    public void whenFindById_thenReturnCustomer() {

        Date localDate = new Date();
        // given
        Customer customer = new Customer();
        customer.setForeName("Vijay");
        customer.setSurName("Babu");
        customer.setBirthDate(localDate);
        entityManager.persist(customer);
        entityManager.flush();

        // when
        Optional<Customer> found = customerRepository.findById(1l);

        // then
        assert(found.get().getForeName()).equals(customer.getForeName());
        assert(found.get().getSurName()).equals(customer.getSurName());
        assert(found.get().getBirthDate()).equals(customer.getBirthDate());
    }
}
