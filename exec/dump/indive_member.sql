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
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_seq` bigint NOT NULL,
  `create_date` datetime NOT NULL,
  `create_member` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_member` varchar(255) DEFAULT NULL,
  `member_background_origin` varchar(255) DEFAULT NULL,
  `member_background_uuid` varchar(255) DEFAULT NULL,
  `member_email` varchar(255) DEFAULT NULL,
  `member_image_origin` varchar(255) DEFAULT NULL,
  `member_image_uuid` varchar(255) DEFAULT NULL,
  `member_nickname` varchar(255) DEFAULT NULL,
  `member_notice` varchar(255) DEFAULT NULL,
  `member_password` varchar(255) DEFAULT NULL,
  `member_profile_message` varchar(255) DEFAULT NULL,
  `member_role` varchar(255) DEFAULT NULL,
  `member_wallet` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`member_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (87,'2022-10-06 13:44:18',NULL,'2022-10-06 13:44:18',NULL,'default_background.png','default_background.png','aaa','default_profile.png','default_profile.png','aaa',NULL,'{bcrypt}$2a$10$WAJQAzGlf/10jy7t1iIQQOs/oY/iCU2zcyZuUIgyzUzO0h1GBC3iO','안녕하세요','ROLE_USER','0xa99670e89e94978423ba54f8a289b2d565590e62'),(88,'2022-10-06 13:44:35',NULL,'2022-10-06 13:47:42',NULL,'default_background.png','default_background.png','jj@jj.com','crew_1665064059821.jpg',NULL,'이상욱',NULL,'{bcrypt}$2a$10$2buCJ7sNHmdu.VCTer9KKOjiRt6hiVM.MeL2NWPyLn/Pgbo7whPva','안녕하세요','ROLE_USER','0x67daa8eb0c6f37b92ea5f15bdca39e874216fb3f'),(89,'2022-10-06 13:45:13',NULL,'2022-10-06 13:56:47',NULL,'crew_1665064603619.jpg','cd78c20f-7c7c-411c-af33-7c0233eaaf9f.jpg','aa@aa','crew_1665064592511.jpg','cb90f7bf-58ba-48ac-95cb-33705aa446c2.jpg','명범',NULL,'{bcrypt}$2a$10$JkI9.7/5RZaZ404iDFdareObtzerurDNQx95e1EslYHMIrkvi.LXm','안녕하세요 뮤지션 이명범입니다.','ROLE_USER','0xd335544e6bc594e0cfa80d9cbecb428eddc90545'),(91,'2022-10-06 13:47:39',NULL,'2022-10-06 13:58:12',NULL,'crew_1665064683487.jpg','12bd07a0-7a80-49de-906a-15a3aa7ba5cb.jpg','jy','crew_1665064688223.jpg','4800668d-690e-4b02-9a42-1141bd1103e4.jpg','정윤',NULL,'{bcrypt}$2a$10$ofxX5J7bIaEiPZ6LCgpPwOHYRGB42aSEyy8CMVIisYOAxUgxUePJe','안녕하세요','ROLE_USER','0x865b1e8fc785b1da5ac66509d69edecc5e602eac'),(95,'2022-10-06 13:52:16',NULL,'2022-10-06 13:52:16',NULL,'default_background.png','default_background.png','mungmnb7777@gmail.com','default_profile.png','default_profile.png','명범',NULL,'{bcrypt}$2a$10$KJ9UoFds1BzBVLmLJf4zueWefbkzpCnCxYO4rtn9tVMuLDb/qx2KG','안녕하세요','ROLE_USER','지갑'),(97,'2022-10-06 13:59:42',NULL,'2022-10-06 14:00:17',NULL,'crew_1665064812570.jpg','a86320fb-a21e-4084-8846-b9450fb8b2aa.jpg','zx','crew_1665064807042.jpg','78b8e72c-1025-4d95-88d9-1c757766a60f.jpg','zx',NULL,'{bcrypt}$2a$10$ijXL.spQa6NOCgfFF5zz4eO5tGFhUxNRiXo4BofGX3ZVXsn6tSdhC','안녕하세요','ROLE_USER','0xb85dd724ebe73d81191335c419f637b8424bf9f6'),(98,'2022-10-06 14:01:09',NULL,'2022-10-06 14:01:09',NULL,'default_background.png','default_background.png','fgfg','default_profile.png','default_profile.png','1212',NULL,'{bcrypt}$2a$10$Zxb2j63TxGgtVYKM8UofouvSzne9DPcvvZ0PCGtAmZftBFFm19a5.','안녕하세요','ROLE_USER','0x3f5a7a027cf9fda447ceb4aed88d95183b5c8a04'),(99,'2022-10-06 14:01:34',NULL,'2022-10-06 14:01:34',NULL,'default_background.png','default_background.png','zc','default_profile.png','default_profile.png','zc',NULL,'{bcrypt}$2a$10$TogXsCOO58kPAZ85mZDOa.2exI.cMrS1VkgSUZuyje6Y7q2OXt68K','안녕하세요','ROLE_USER','0xd9d489316b502378951088b8bbb99c59fd551191'),(100,'2022-10-06 14:04:18',NULL,'2022-10-06 14:07:07',NULL,'crew_1665065084653.jpg','a9a5e514-2fe5-4ff5-94a1-e40af6d78853.jpg','jj','crew_1665065081295.jpg','b7b1b44a-150e-4a39-80a6-f1b6b6e2e3ee.jpg','정윤',NULL,'{bcrypt}$2a$10$RM55zmfhVNa/ZUG.yNeDK.Phya53oxWEbrPfsGsKk2hCXcFQtNLKK','안녕하세요','ROLE_USER','0x46dd39abb10af01032f253e538c224a55fdb9fcb'),(101,'2022-10-06 14:11:13',NULL,'2022-10-06 14:11:13',NULL,'default_background.png','default_background.png','mungmnb777@gmail.com','default_profile.png','default_profile.png','명범',NULL,'{bcrypt}$2a$10$QS4GmIFEbRI/PJPKkZbIe./6eR9g0GocpyXys3LI4iTyifK2MU1eS','안녕하세요','ROLE_USER','지갑'),(103,'2022-10-06 14:14:15',NULL,'2022-10-06 14:15:21',NULL,'default_background.png','default_background.png','abc','default_profile.png','default_profile.png','SW',NULL,'{bcrypt}$2a$10$VzxNrDr8IKmY38kwb6eI0enPmC9jdHz0F7Xbha144jJCw.PmgXjgm','안녕하세요','ROLE_USER','0x71d1d68ceb22c575360246febd7660fc10a388a4'),(104,'2022-10-06 14:17:23',NULL,'2022-10-06 14:27:55',NULL,'default_background.png','default_background.png','cba','default_profile.png','default_profile.png','상상',NULL,'{bcrypt}$2a$10$SkdHRNKBplM.VY.lg46SX.31ikHoUFoPDIHJSCDVe/E9GARC/gXkG','안녕하세요','ROLE_USER','0x34ebe2e1a7e2097eed45a4dc1a85b9eb86c5d5f5'),(106,'2022-10-06 14:25:27',NULL,'2022-10-06 14:25:27',NULL,'default_background.png','default_background.png','bb','default_profile.png','default_profile.png','bb',NULL,'{bcrypt}$2a$10$O4Z4zlpHgCs.ZM8cLhLJCOQ9FUc7fyvXvnC/oKVZV11xvqgsSpLTS','안녕하세요','ROLE_USER','0x5d468bf512e21f133d9162fc03241bf5101cf8f4'),(116,'2022-10-06 15:22:15',NULL,'2022-10-06 15:23:05',NULL,'crew_1665069780133.jpg','79685af7-b56a-4ea3-b909-cff3f4607465.jpg','aaa@aaa.com','crew_1665069775643.jpg','5bd484d9-0b90-4a1b-a4d7-eb7914cc14ae.jpg','123',NULL,'{bcrypt}$2a$10$XveYcq44uIxIxtcC7UZiTujwlh/t4vF6JZNTJiZgvTIgkoqgvc9LK','안녕하세요','ROLE_USER','0xd26b57b086c9968dfbf853ce1ad83c9d8e5eb183'),(121,'2022-10-06 15:36:50',NULL,'2022-10-06 15:36:50',NULL,'default_background.png','default_background.png','pp','default_profile.png','default_profile.png','인디',NULL,'{bcrypt}$2a$10$Gbntgkkbw5tX9fcH2xv7jeXnL9jufe8kJaqKCBjP17jrHRHRZNqk2','안녕하세요','ROLE_USER','0x78df174e6847bb821680615ee01c4e08747684e7'),(122,'2022-10-06 15:37:50',NULL,'2022-10-06 15:38:20',NULL,'crew_1665070695840.jpg','b7ae65a9-2b52-4d0b-9267-0014280b46d2.jpg','kk','crew_1665070683476.jpg','1ec4b36b-b567-46b2-b870-4935d5bb7b8c.jpg','인디',NULL,'{bcrypt}$2a$10$BaklCicwp3H.VM5raA51teoOzzvQvtS.LHTQ66WNUpwspRjSco7b.','안녕하세요','ROLE_USER','0x8e8a43eda0f6c1197ff8445a5497cd91d5f98d2a'),(132,'2022-10-06 15:51:41',NULL,'2022-10-06 15:52:36',NULL,'crew_1665071551274.jpg','7b180154-24c6-407d-8270-d708a1fc5958.jpg','ll','crew_1665071536094.jpg','a4a1e6d2-8936-4b51-aac8-dc0e3204bae7.jpg','뮤뮤',NULL,'{bcrypt}$2a$10$DppkVOfvaSHJNqqT40r5Qe6h5CbDLzraZNmKHoT5M4J/jJYusWcWC','안녕하세요','ROLE_USER','0x7ed4d21b99afd3c349ab83c31a91e343c0e31cde'),(139,'2022-10-06 16:49:39',NULL,'2022-10-06 16:49:39',NULL,'default_background.png','default_background.png','jdsaeyqi@naver.com11','default_profile.png','default_profile.png','이상',NULL,'{bcrypt}$2a$10$Jb4CfPlnfD2MXpE0eGCgT.KylRWCJIKIovy4g810V607vkCOtrs.W','안녕하세요','ROLE_USER','0x0abae23c1d1636e33ec769287d9ae6b7e6997641'),(140,'2022-10-06 16:54:31',NULL,'2022-10-06 16:54:31',NULL,'default_background.png','default_background.png','jdsaeyqi@naver.com1','default_profile.png','default_profile.png','이상',NULL,'{bcrypt}$2a$10$Y/uWYyT.vshHbx0ylujbIebUYxiXl4tbdGFpDN5HFONo44n1yHlqK','안녕하세요','ROLE_USER','0xaa4e1b92e1d6e46b0250846857f5dd16607be218');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-07  2:42:04
