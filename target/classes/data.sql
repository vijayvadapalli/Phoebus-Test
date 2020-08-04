insert into customer (customer_id, fore_name, sur_name, birth_date) values(10001, 'Vijay', 'Babu', sysdate());
insert into customer (customer_id, fore_name, sur_name, birth_date) values(10002, 'Tom', 'Field', sysdate());
insert into customer (customer_id, fore_name, sur_name, birth_date) values(10003, 'Richard', 'Seans', sysdate());
insert into account (account_id, customer_id, account_number) values(11001, 10001, 14001);
insert into account (account_id, customer_id, account_number) values(11002, 10001, 14002);
insert into account (account_id, customer_id, account_number) values(11003, 10001, 14003);
insert into account (account_id, customer_id, account_number) values(11004, 10002, 14003);
insert into account (account_id, customer_id, account_number) values(11005, 10002, 14005);
insert into account (account_id, customer_id, account_number) values(11006, 10003, 14006);