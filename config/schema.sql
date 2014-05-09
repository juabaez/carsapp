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
  number INT NOT NULL PRIMARY KEY,
  user_email VARCHAR(40) NOT NULL,
  CONSTRAINT fk_phones_users
    FOREIGN KEY (user_email)
    REFERENCES users (email)
);

/**********************/
/* Table for 'Vehicle' */
DROP TABLE IF EXISTS vehicles;
CREATE TABLE vehicles (
  id int NOT NULL auto_increment PRIMARY KEY,
  brand VARCHAR(45) NOT NULL,
  name VARCHAR(45) NOT NULL,
  year INT(4) NOT NULL
);

/**********************/
/* Table for 'Car' */
DROP TABLE IF EXISTS cars;
CREATE TABLE cars (
  id int NOT NULL PRIMARY KEY,
  max_capacity INT NOT NULL,
  CONSTRAINT fk_cars_vehicles
    FOREIGN KEY (id)
    REFERENCES vehicles (id)
);

/**********************/
/* Table for 'Motorcycle' */
DROP TABLE IF EXISTS motorcycles;
CREATE TABLE motorcycles (
  id int NOT NULL PRIMARY KEY,
  cc INT NOT NULL,
  CONSTRAINT fk_motorcycles_vehicles
    FOREIGN KEY (id)
    REFERENCES vehicles (id)
);

/**********************/
/* Table for 'Truck' */
DROP TABLE IF EXISTS trucks;
CREATE TABLE trucks (
  id int NOT NULL PRIMARY KEY,
  heigth INT NOT NULL,
  max_capacity INT NOT NULL,
  max_long INT NOT NULL,
  CONSTRAINT fk_trucks_vehicles
    FOREIGN KEY (id)
    REFERENCES vehicles (id)
);

/**********************/
/* Table for 'Other' */
DROP TABLE IF EXISTS others;
CREATE TABLE others (
  id int NOT NULL PRIMARY KEY,
  max_capacity INT NOT NULL,
  type VARCHAR(45) NOT NULL,
  CONSTRAINT fk_others_vehicles
    FOREIGN KEY (id)
    REFERENCES vehicles (id)
);

/**********************/
/* Table for 'Post' */
DROP TABLE IF EXISTS posts;
CREATE TABLE posts (
  id int NOT NULL auto_increment PRIMARY KEY,
  text VARCHAR(45) NOT NULL,
  vehicle_id INT NOT NULL,
  user_email VARCHAR(45) NOT NULL,
  CONSTRAINT fk_posts_vehicles
    FOREIGN KEY (vehicle_id)
    REFERENCES vehicles (id),
  CONSTRAINT fk_posts_users
    FOREIGN KEY (user_email)
    REFERENCES users (email)
);

/**********************/
/* Table for 'Owner' relation */
DROP TABLE IF EXISTS owner;
CREATE TABLE owner (
  user_email VARCHAR(45) NOT NULL,
  vehicle_id INT NOT NULL,
  PRIMARY KEY (user_email, vehicle_id),
  CONSTRAINT fk_users_has_vehicles_users
    FOREIGN KEY (user_email)
    REFERENCES users (email),
  CONSTRAINT fk_users_has_vehicles
    FOREIGN KEY (vehicle_id)
    REFERENCES vehicles (id)
);

/**********************/
/* Table for 'Rate' */
DROP TABLE IF EXISTS rates;
CREATE TABLE rates (
  id int NOT NULL auto_increment PRIMARY KEY,
  rate INT NOT NULL,
  post_id INT NOT NULL,
  user_email VARCHAR(45) NOT NULL,
  CONSTRAINT fk_rates_posts
    FOREIGN KEY (post_id)
    REFERENCES posts (id),
  CONSTRAINT fk_rates_users
    FOREIGN KEY (user_email)
    REFERENCES users (email)
);

/**********************/
/* Table for 'Question' */
DROP TABLE IF EXISTS questions;
CREATE TABLE questions (
  id int NOT NULL auto_increment PRIMARY KEY,
  question VARCHAR(80) NOT NULL,
  post_id INT NOT NULL,
  user_email VARCHAR(60) NOT NULL,
  CONSTRAINT fk_questions_posts
    FOREIGN KEY (post_id)
    REFERENCES posts (id),
  CONSTRAINT fk_questions_users
    FOREIGN KEY (user_email)
    REFERENCES users (email)
);

/**********************/
/* Table for 'Answer' */
DROP TABLE IF EXISTS answers;
CREATE TABLE answers (
  id int NOT NULL auto_increment PRIMARY KEY,
  answer VARCHAR(80) NOT NULL,
  question_id INT NOT NULL,
  user_email VARCHAR(60) NOT NULL,
  CONSTRAINT fk_answers_questions
    FOREIGN KEY (question_id)
    REFERENCES questions (id),
  CONSTRAINT fk_answers_users
    FOREIGN KEY (user_email)
    REFERENCES users (email)
);
