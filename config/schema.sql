-- Integrantes: W - X - Y - Z

DROP TABLE IF EXISTS users; -- Usuarios
CREATE TABLE users(
    id INT DEFAULT NULL auto_increment PRIMARY KEY,
    email VARCHAR(60) NOT NULL UNIQUE,
    first_name VARCHAR(30),
    last_name VARCHAR(30),
    adress VARCHAR(60)
);

DROP TABLE IF EXISTS vehicles; -- Vehiculos
CREATE TABLE vehicles(
    id INT DEFAULT NULL auto_increment PRIMARY KEY,
    brand VARCHAR(30),
    year INT(4) UNSIGNED,
    name VARCHAR(30)
);

