package com.phoebus.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Min;

@ApiModel(description="All details about the account.")
@Entity
public class Account {

    @Id
    @GeneratedValue
    @Column(name="accountId")
    private long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="CUSTOMER_ID")
    private Customer customer;

    @Min(value=1000, message="must be equal or greater than 1000")
    @ApiModelProperty(notes="Account number should be greater than 1000")
    @Column(name="accountNumber")
    private int accountNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "Account{" +
                "account Id=" + id +
                ", accountNumber=" + accountNumber +
                '}';
    }
}
