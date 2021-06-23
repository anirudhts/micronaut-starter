CREATE TABLE customers
( customer_id number(10) NOT NULL,
  customer_name varchar2(50) NOT NULL,
  city varchar2(50),
  phone_number number(10),
  CONSTRAINT customers_pk PRIMARY KEY (customer_id)
);