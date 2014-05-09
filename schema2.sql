-- MySQL dump 10.13  Distrib 5.5.37, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: carsapp_test
-- ------------------------------------------------------
-- Server version	5.5.37-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answers` (
  `answer_id` int(11) NOT NULL,
  `answer` varchar(45) NOT NULL,
  `questions_question_id` int(11) NOT NULL,
  `users_email` varchar(45) NOT NULL,
  PRIMARY KEY (`answer_id`),
  KEY `fk_answers_questions1` (`questions_question_id`),
  KEY `fk_answers_users1` (`users_email`),
  CONSTRAINT `fk_answers_questions1` FOREIGN KEY (`questions_question_id`) REFERENCES `questions` (`question_id`),
  CONSTRAINT `fk_answers_users1` FOREIGN KEY (`users_email`) REFERENCES `users` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cars` (
  `max_capacity` int(11) NOT NULL,
  `vehicle_id` int(11) NOT NULL,
  PRIMARY KEY (`vehicle_id`),
  CONSTRAINT `fk_cars_vehicles1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`vehicle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `citys`
--

DROP TABLE IF EXISTS `citys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `citys` (
  `name` varchar(20) NOT NULL,
  `state` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `postcode` int(11) NOT NULL,
  PRIMARY KEY (`postcode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `citys`
--

LOCK TABLES `citys` WRITE;
/*!40000 ALTER TABLE `citys` DISABLE KEYS */;
/*!40000 ALTER TABLE `citys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `esDuenio`
--

DROP TABLE IF EXISTS `esDuenio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `esDuenio` (
  `users_email` varchar(45) NOT NULL,
  `vehicles_vehicle_id` int(11) NOT NULL,
  PRIMARY KEY (`users_email`,`vehicles_vehicle_id`),
  KEY `fk_users_has_vehicles_vehicles1` (`vehicles_vehicle_id`),
  CONSTRAINT `fk_users_has_vehicles_users1` FOREIGN KEY (`users_email`) REFERENCES `users` (`email`),
  CONSTRAINT `fk_users_has_vehicles_vehicles1` FOREIGN KEY (`vehicles_vehicle_id`) REFERENCES `vehicles` (`vehicle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `esDuenio`
--

LOCK TABLES `esDuenio` WRITE;
/*!40000 ALTER TABLE `esDuenio` DISABLE KEYS */;
/*!40000 ALTER TABLE `esDuenio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `motorcycles`
--

DROP TABLE IF EXISTS `motorcycles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `motorcycles` (
  `cc` int(11) NOT NULL,
  `vehicle_id` int(11) NOT NULL,
  PRIMARY KEY (`vehicle_id`),
  CONSTRAINT `fk_motorcycles_vehicles1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`vehicle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `motorcycles`
--

LOCK TABLES `motorcycles` WRITE;
/*!40000 ALTER TABLE `motorcycles` DISABLE KEYS */;
/*!40000 ALTER TABLE `motorcycles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `others`
--

DROP TABLE IF EXISTS `others`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `others` (
  `max_capacity` int(11) NOT NULL,
  `type` varchar(45) NOT NULL,
  `vehicle_id` int(11) NOT NULL,
  PRIMARY KEY (`vehicle_id`),
  CONSTRAINT `fk_others_vehicles1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`vehicle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `others`
--

LOCK TABLES `others` WRITE;
/*!40000 ALTER TABLE `others` DISABLE KEYS */;
/*!40000 ALTER TABLE `others` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phones`
--

DROP TABLE IF EXISTS `phones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phones` (
  `type` varchar(20) NOT NULL,
  `number` int(11) NOT NULL,
  `users_email` varchar(45) NOT NULL,
  PRIMARY KEY (`number`),
  KEY `fk_phones_users1` (`users_email`),
  CONSTRAINT `fk_phones_users1` FOREIGN KEY (`users_email`) REFERENCES `users` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phones`
--

LOCK TABLES `phones` WRITE;
/*!40000 ALTER TABLE `phones` DISABLE KEYS */;
/*!40000 ALTER TABLE `phones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `posts` (
  `post_id` int(11) NOT NULL,
  `text` varchar(45) NOT NULL,
  `vehicles_vehicle_id` int(11) NOT NULL,
  `users_email` varchar(45) NOT NULL,
  PRIMARY KEY (`post_id`),
  KEY `fk_posts_vehicles1` (`vehicles_vehicle_id`),
  KEY `fk_posts_users1` (`users_email`),
  CONSTRAINT `fk_posts_users1` FOREIGN KEY (`users_email`) REFERENCES `users` (`email`),
  CONSTRAINT `fk_posts_vehicles1` FOREIGN KEY (`vehicles_vehicle_id`) REFERENCES `vehicles` (`vehicle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `question_id` int(11) NOT NULL,
  `question` varchar(45) NOT NULL,
  `posts_post_id` int(11) NOT NULL,
  `users_email` varchar(45) NOT NULL,
  PRIMARY KEY (`question_id`),
  KEY `fk_questions_posts1` (`posts_post_id`),
  KEY `fk_questions_users1` (`users_email`),
  CONSTRAINT `fk_questions_posts1` FOREIGN KEY (`posts_post_id`) REFERENCES `posts` (`post_id`),
  CONSTRAINT `fk_questions_users1` FOREIGN KEY (`users_email`) REFERENCES `users` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rates`
--

DROP TABLE IF EXISTS `rates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rates` (
  `rate` int(11) NOT NULL,
  `posts_post_id1` int(11) NOT NULL,
  `users_email` varchar(45) NOT NULL,
  PRIMARY KEY (`rate`),
  KEY `fk_rates_posts1` (`posts_post_id1`),
  KEY `fk_rates_users1` (`users_email`),
  CONSTRAINT `fk_rates_posts1` FOREIGN KEY (`posts_post_id1`) REFERENCES `posts` (`post_id`),
  CONSTRAINT `fk_rates_users1` FOREIGN KEY (`users_email`) REFERENCES `users` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rates`
--

LOCK TABLES `rates` WRITE;
/*!40000 ALTER TABLE `rates` DISABLE KEYS */;
/*!40000 ALTER TABLE `rates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trucks`
--

DROP TABLE IF EXISTS `trucks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trucks` (
  `heigth` int(11) NOT NULL,
  `max_capacity` int(11) NOT NULL,
  `max_long` int(11) NOT NULL,
  `vehicle_id` int(11) NOT NULL,
  PRIMARY KEY (`vehicle_id`),
  CONSTRAINT `fk_trucks_vehicles1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`vehicle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trucks`
--

LOCK TABLES `trucks` WRITE;
/*!40000 ALTER TABLE `trucks` DISABLE KEYS */;
/*!40000 ALTER TABLE `trucks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `adress` varchar(45) NOT NULL,
  `citys_postcode` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_users_citys1` (`citys_postcode`),
  CONSTRAINT `fk_users_citys1` FOREIGN KEY (`citys_postcode`) REFERENCES `citys` (`postcode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicles`
--

DROP TABLE IF EXISTS `vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicles` (
  `vehicle_id` int(11) NOT NULL,
  `brand` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `year` int(11) NOT NULL,
  PRIMARY KEY (`vehicle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicles`
--

LOCK TABLES `vehicles` WRITE;
/*!40000 ALTER TABLE `vehicles` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehicles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-08 21:05:56
