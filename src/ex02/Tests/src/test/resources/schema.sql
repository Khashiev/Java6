DROP SCHEMA IF EXISTS tests CASCADE;
CREATE SCHEMA IF NOT EXISTS tests;

CREATE TABLE IF NOT EXISTS tests.product (
    id bigint PRIMARY KEY NOT NULL,
    name varchar(30) NOT NULL,
    price decimal NOT NULL CHECK ( price > 0 )
);