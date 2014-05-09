/**********************/
/* Table for 'City' */
DROP TABLE IF EXISTS cities;
CREATE TABLE cities (
  postcode INT NOT NULL PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  state VARCHAR(20) NOT NULL,
  country VARCHAR(20) NOT NULL
);

/**********************/
/* Table for 'User' */
DROP TABLE IF EXISTS users;
CREATE TABLE users(
  id INT NOT NULL auto_increment PRIMARY KEY,
  first_name VARCHAR(40) NOT NULL,
  last_name VARCHAR(40) NOT NULL,
  password VARCHAR(40) NOT NULL,
  email VARCHAR(60) NOT NULL unique,
  adress VARCHAR(40) NOT NULL,
  city_postcode INT NOT NULL
);

/**********************/
/* Table for 'Phone' */
DROP TABLE IF EXISTS phones;
CREATE TABLE phones (
  type VARCHAR(20) NOT NULL,
  cel_number INT NOT NULL PRIMARY KEY,
  user_id int NOT NULL
);

/**********************/
/* Table for 'Vehicle' */
DROP TABLE IF EXISTS vehicles;
CREATE TABLE vehicles (
  id int NOT NULL auto_increment PRIMARY KEY,
  brand VARCHAR(45) NOT NULL,
  name VARCHAR(45) NOT NULL,
  year INT(4) NOT NULL,
  user_id INT NOT NULL
);

/**********************/
/* Table for 'Car' */
DROP TABLE IF EXISTS cars;
CREATE TABLE cars (
  id int NOT NULL PRIMARY KEY,
  vehicle_id int NOT NULL,
  max_capacity INT NOT NULL
);

/**********************/
/* Table for 'Motorcycle' */
DROP TABLE IF EXISTS motorcycles;
CREATE TABLE motorcycles (
  id int NOT NULL PRIMARY KEY,
  vehicle_id int NOT NULL,
  cilindrada INT NOT NULL
);

/**********************/
/* Table for 'Truck' */
DROP TABLE IF EXISTS trucks;
CREATE TABLE trucks (
  id int NOT NULL PRIMARY KEY,
  vehicle_id int NOT NULL,
  heigth INT NOT NULL,
  max_capacity INT NOT NULL,
  max_long INT NOT NULL
);

/**********************/
/* Table for 'Other' */
DROP TABLE IF EXISTS others;
CREATE TABLE others (
  id int NOT NULL PRIMARY KEY,
  vehicle_id int NOT NULL,
  max_capacity INT,
  type VARCHAR(45) NOT NULL
);

/**********************/
/* Table for 'Post' */
DROP TABLE IF EXISTS posts;
CREATE TABLE posts (
  id int NOT NULL auto_increment PRIMARY KEY,
  text VARCHAR(45) NOT NULL,
  vehicle_id INT NOT NULL,
  user_id INT NOT NULL
);


/**********************/
/* Table for 'Rate' */
DROP TABLE IF EXISTS rates;
CREATE TABLE rates (
  id int NOT NULL auto_increment PRIMARY KEY,
  rate INT NOT NULL,
  post_id INT NOT NULL,
  user_id int NOT NULL
);

/**********************/
/* Table for 'Question' */
DROP TABLE IF EXISTS questions;
CREATE TABLE questions (
  id int NOT NULL auto_increment PRIMARY KEY,
  question VARCHAR(80) NOT NULL,
  post_id INT NOT NULL,
  user_id int NOT NULL
);

/**********************/
/* Table for 'Answer' */
DROP TABLE IF EXISTS answers;
CREATE TABLE answers (
  id int NOT NULL auto_increment PRIMARY KEY,
  answer VARCHAR(80) NOT NULL,
  question_id INT NOT NULL,
  user_id int NOT NULL
);
