package com.phoebus.test.dao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@ApiModel(description="All details about the customer.")
@Entity
public class Customer {

    @Id
    @GeneratedValue
    @Column(name="customerId")
    private long id;

    @OneToMany(mappedBy="customer", cascade = CascadeType.REMOVE)
    private List<Account> accounts;

    @Size(min=2, message="Forename should have atleast 3 characters")
    @ApiModelProperty(notes="Forename should have atleast 3 characters")
    @Column(name="foreName")
    private String foreName;

    @Size(min=2, message="Surname should have atleast 2 characters")
    @ApiModelProperty(notes="Surname should have atleast 2 characters")
    @Column(name="surName")
    private String surName;

    @Past
    @ApiModelProperty(notes="Birth date should be in the past")
    @Column(name="birthDate")
    private Date birthDate;


    public Customer() {
    }

    public Customer(long customerId, @Size(min = 2, message = "Forename should have atleast 3 characters") String foreName, @Size(min = 2, message = "Surname should have atleast 2 characters") String surName, @Past Date birthDate) {
        super();
        this.id = customerId;
        this.foreName = foreName;
        this.surName = surName;
        this.birthDate = birthDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long customerId) {
        this.id = customerId;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getForeName() {
        return foreName;
    }

    public void setForeName(String foreName) {
        this.foreName = foreName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", foreName='" + foreName + '\'' +
                ", surName='" + surName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
