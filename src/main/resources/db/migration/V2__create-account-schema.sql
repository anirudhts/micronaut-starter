CREATE TABLE accounts
( account_id number(10) NOT NULL,
  customer_id number(10) NOT NULL,
  account_type varchar2(50) NOT NULL,
  branch varchar2(50) NOT NULL,
  CONSTRAINT accounts_pk PRIMARY KEY (account_id),
  FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);