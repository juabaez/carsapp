-- Integrantes: W - X - Y - Z

DROP TABLE IF EXISTS users; -- Usuarios
CREATE TABLE users(
    email VARCHAR(60) NOT NULL UNIQUE,
    first_name VARCHAR(30),
    last_name VARCHAR(30),
    adress VARCHAR(60),
    PRIMARY KEY (email)
);

DROP TABLE IF EXISTS vehicles; -- Vehiculos
CREATE TABLE vehicles(
    vehicle_id INT NOT NULL AUTO_INCREMENT,
    brand VARCHAR(30),
    year INT(4) UNSIGNED,
    name VARCHAR(30),
    PRIMARY KEY (vehicle_id)
);

