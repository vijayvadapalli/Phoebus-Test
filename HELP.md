# Know your Customer - Test

### To open it in swaggerUI go to URL http://localhost:8080 in browser (Chrome)
The following REST URL's are supported

* [Get all Customers](http://localhost:8080/customers)
* [Get a Customer](http://localhost:8080/customer/10001)
* [Create a new customer (Use HTTP POST)](http://localhost:8080/customer)
    - Validations:
        - foreName should be at least 3 chars long
        - surName should be at least 2 chars long
        - birthDate should bot be in past

    - Sample json
        - {
          "foreName": "Tom",
          "surName": "Field",
          "birthDate": "2020-08-03T23:00:00.000+00:00"
        }
* [Delete a Customers](http://localhost:8080/customer/10001)
* [Get account for a Customer](http://localhost:8080/customer/10003/accounts)
* [List all Accounts](http://localhost:8080/accounts)
* [Create a account for a Customer (Use HTTP Post)](http://localhost:8080/customer/10002/accounts)

    - Validations: 
        - accountNumber should be greater than 1000
        - Sample json, which will create accountNumber 14005 for Customer 10002
        
        {
                "accountNumber": 14005
        }


### Security is disabled by default.
 * To enable it just uncomment the following section in pom.xml
 
          <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-security</artifactId>
         </dependency>
 * Username and password set are as follows
      - spring.security.user.name=phoebus
      - spring.security.user.password=test        