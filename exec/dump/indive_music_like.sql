-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: j7d102.p.ssafy.io    Database: indive
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `music_like`
--

DROP TABLE IF EXISTS `music_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `music_like` (
  `ml_liker_seq` bigint NOT NULL,
  `ml_music_seq` bigint NOT NULL,
  `create_date` datetime NOT NULL,
  `create_member` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_member` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ml_liker_seq`,`ml_music_seq`),
  KEY `FKcn7faru3gftnbthvv01lu9bh8` (`ml_music_seq`),
  CONSTRAINT `FKbugjks51r58jk5mf3d63j7gfv` FOREIGN KEY (`ml_liker_seq`) REFERENCES `member` (`member_seq`),
  CONSTRAINT `FKcn7faru3gftnbthvv01lu9bh8` FOREIGN KEY (`ml_music_seq`) REFERENCES `music` (`music_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music_like`
--

LOCK TABLES `music_like` WRITE;
/*!40000 ALTER TABLE `music_like` DISABLE KEYS */;
INSERT INTO `music_like` VALUES (89,107,'2022-10-06 15:57:00',NULL,'2022-10-06 15:57:00',NULL),(116,102,'2022-10-06 15:25:15',NULL,'2022-10-06 15:25:15',NULL),(140,107,'2022-10-06 17:03:09',NULL,'2022-10-06 17:03:09',NULL);
/*!40000 ALTER TABLE `music_like` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-07  2:42:05
