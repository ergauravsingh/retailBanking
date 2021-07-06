# retailBanking


// SQL scripts

show databases;
use bankdb;
show tables;
select * from appuser;
select * from account;
select * from customer_entity;
select * from transaction;
select * from account, customer_entity;

// insert this entry for initial employee login
insert into appuser (userid,username,password,role) values ('EMPLOYEE101','emp','emp','EMPLOYEE');
