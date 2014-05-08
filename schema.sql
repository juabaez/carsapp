SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Table esDuenio
-- -----------------------------------------------------
DROP TABLE IF EXISTS esDuenio;
CREATE TABLE esDuenio (
  users_email VARCHAR(45) NOT NULL,
  vehicles_vehicle_id INT NOT NULL,
  PRIMARY KEY (users_email, vehicles_vehicle_id),
  CONSTRAINT fk_users_has_vehicles_users1
    FOREIGN KEY (users_email)
    REFERENCES users (email),
  CONSTRAINT fk_users_has_vehicles_vehicles1
    FOREIGN KEY (vehicles_vehicle_id)
    REFERENCES vehicles (vehicle_id)
);


-- -----------------------------------------------------
-- Table users
-- -----------------------------------------------------
DROP TABLE IF EXISTS users;
CREATE TABLE users(
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  password VARCHAR(45) NOT NULL,
  email VARCHAR(45) NOT NULL unique,
  adress VARCHAR(45) NOT NULL,
  citys_postcode INT NOT NULL,
  id int NOT NULL PRIMARY KEY,
  CONSTRAINT fk_users_citys1
    FOREIGN KEY (citys_postcode)
    REFERENCES citys (postcode)
);


-- -----------------------------------------------------
-- Table citys
-- -----------------------------------------------------
DROP TABLE IF EXISTS citys;
CREATE TABLE citys (
  name VARCHAR(20) NOT NULL,
  state VARCHAR(45) NOT NULL,
  country VARCHAR(45) NOT NULL,
  postcode INT NOT NULL PRIMARY KEY
);


-- -----------------------------------------------------
-- Table phones
-- -----------------------------------------------------
DROP TABLE IF EXISTS phones;
CREATE TABLE phones (
  type VARCHAR(20) NOT NULL,
  number INT NOT NULL,
  users_email VARCHAR(45) NOT NULL,
  PRIMARY KEY (number),
  CONSTRAINT fk_phones_users1
    FOREIGN KEY (users_email)
    REFERENCES users (email)
);



-- -----------------------------------------------------
-- Table cars
-- -----------------------------------------------------
DROP TABLE IF EXISTS cars;
CREATE TABLE cars (
  max_capacity INT NOT NULL ,
  vehicle_id INT NOT NULL,
  PRIMARY KEY (vehicle_id),
  CONSTRAINT fk_cars_vehicles1
    FOREIGN KEY (vehicle_id)
    REFERENCES vehicles (vehicle_id)
);


-- -----------------------------------------------------
-- Table motorcycles
-- -----------------------------------------------------
DROP TABLE IF EXISTS motorcycles;
CREATE TABLE motorcycles (
  cc INT NOT NULL,
  vehicle_id int NOT NULL ,
  PRIMARY KEY (vehicle_id),
  CONSTRAINT fk_motorcycles_vehicles1
    FOREIGN KEY (vehicle_id)
    REFERENCES vehicles (vehicle_id)
);


-- -----------------------------------------------------
-- Table trucks
-- -----------------------------------------------------
DROP TABLE IF EXISTS trucks;
CREATE TABLE trucks (
  heigth INT NOT NULL,
  max_capacity INT NOT NULL,
  max_long INT NOT NULL,
  vehicle_id INT NOT NULL ,
  PRIMARY KEY (vehicle_id),
  CONSTRAINT fk_trucks_vehicles1
    FOREIGN KEY (vehicle_id)
    REFERENCES vehicles (vehicle_id)
);


-- -----------------------------------------------------
-- Table others
-- -----------------------------------------------------
DROP TABLE IF EXISTS others;
CREATE TABLE others (
  max_capacity INT NOT NULL,
  type VARCHAR(45) NOT NULL,
  vehicle_id INT NOT NULL ,
  PRIMARY KEY (vehicle_id),
  CONSTRAINT fk_others_vehicles1
    FOREIGN KEY (vehicle_id)
    REFERENCES vehicles (vehicle_id)
);

-- -----------------------------------------------------
-- Table rates
-- -----------------------------------------------------
DROP TABLE IF EXISTS rates;
CREATE TABLE rates (
  rate INT NOT NULL,
  posts_post_id1 INT NOT NULL,
  users_email VARCHAR(45) NOT NULL,
  PRIMARY KEY (rate),
  CONSTRAINT fk_rates_posts1
    FOREIGN KEY (posts_post_id1)
    REFERENCES posts (post_id),
  CONSTRAINT fk_rates_users1
    FOREIGN KEY (users_email)
    REFERENCES users (email)
);


-- -----------------------------------------------------
-- Table posts
-- -----------------------------------------------------
DROP TABLE IF EXISTS posts;
CREATE TABLE posts (
  post_id INT NOT NULL ,
  text VARCHAR(45) NOT NULL,
  vehicles_vehicle_id INT NOT NULL,
  users_email VARCHAR(45) NOT NULL,
  PRIMARY KEY (post_id),
  CONSTRAINT fk_posts_vehicles1
    FOREIGN KEY (vehicles_vehicle_id)
    REFERENCES vehicles (vehicle_id),
  CONSTRAINT fk_posts_users1
    FOREIGN KEY (users_email)
    REFERENCES users (email)
);


-- -----------------------------------------------------
-- Table vehicles
-- -----------------------------------------------------
DROP TABLE IF EXISTS vehicles;
CREATE TABLE vehicles (
  vehicle_id INT NOT NULL ,
  brand VARCHAR(45) NOT NULL,
  name VARCHAR(45) NOT NULL,
  year INT NOT NULL,
  PRIMARY KEY (vehicle_id)
);



-- -----------------------------------------------------
-- Table answers
-- -----------------------------------------------------
DROP TABLE IF EXISTS answers;
CREATE TABLE answers (
  answer_id INT NOT NULL,
  answer VARCHAR(45) NOT NULL,
  questions_question_id INT NOT NULL,
  users_email VARCHAR(45) NOT NULL,
  PRIMARY KEY (answer_id),
  CONSTRAINT fk_answers_questions1
    FOREIGN KEY (questions_question_id)
    REFERENCES questions (question_id),
  CONSTRAINT fk_answers_users1
    FOREIGN KEY (users_email)
    REFERENCES users (email)
);


-- -----------------------------------------------------
-- Table questions
-- -----------------------------------------------------
DROP TABLE IF EXISTS questions;
CREATE TABLE questions (
  question_id INT NOT NULL,
  question VARCHAR(45) NOT NULL,
  posts_post_id INT NOT NULL,
  users_email VARCHAR(45) NOT NULL,
  PRIMARY KEY (question_id),
  CONSTRAINT fk_questions_posts1
    FOREIGN KEY (posts_post_id)
    REFERENCES posts (post_id),
  CONSTRAINT fk_questions_users1
    FOREIGN KEY (users_email)
    REFERENCES users (email)
);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
