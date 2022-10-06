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
-- Table structure for table `music`
--

DROP TABLE IF EXISTS `music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `music` (
  `music_seq` bigint NOT NULL,
  `create_date` datetime NOT NULL,
  `create_member` varchar(255) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_member` varchar(255) DEFAULT NULL,
  `music_composer` varchar(255) DEFAULT NULL,
  `music_description` varchar(255) DEFAULT NULL,
  `music_genre` varchar(255) DEFAULT NULL,
  `music_image_origin` varchar(255) DEFAULT NULL,
  `music_image_uuid` varchar(255) DEFAULT NULL,
  `music_like_count` int DEFAULT NULL,
  `music_lyricist` varchar(255) DEFAULT NULL,
  `music_lyrics` longtext,
  `music_file_origin` varchar(255) DEFAULT NULL,
  `music_file_uuid` varchar(255) DEFAULT NULL,
  `music_release_datetime` datetime DEFAULT NULL,
  `music_reservation_datetime` datetime DEFAULT NULL,
  `music_title` varchar(255) DEFAULT NULL,
  `music_author_seq` bigint DEFAULT NULL,
  PRIMARY KEY (`music_seq`),
  KEY `FKmxpxobmfxii2dadk6vfufh6ue` (`music_author_seq`),
  CONSTRAINT `FKmxpxobmfxii2dadk6vfufh6ue` FOREIGN KEY (`music_author_seq`) REFERENCES `member` (`member_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music`
--

LOCK TABLES `music` WRITE;
/*!40000 ALTER TABLE `music` DISABLE KEYS */;
INSERT INTO `music` VALUES (102,'2022-10-06 14:13:30',NULL,'2022-10-06 15:25:15',NULL,'이명범','천재적인 인디 스타 이명범의 신곡\n아련한 발라드','ballad','crew_1665065534532.jpg','8b7e22a1-0355-4a8d-b56d-a2fc00c2d635.jpg',1,'이명범','어느새 길어진\n나의 작은 곳 방안에\n너의 작은 숨결이\n아직 내 눈에 남았네\n돌이켜 보면 모두\n아름다운 꿈이였어\n이제는 날위해\n그손을 꼭 내가 놓을게\n이제는 돌아 갈 수 없는 내\n시간들도\n뒤돌아보면 비웃음 뿐인 멍청이도\n일방향적인 사랑을 해도 난\n너를 가질수만 있다면\n꼭 흔적을 꼭 남길게\n니 뒤에 내가 남아 옆에\n신경을 안쓸수가없게\n딱붙어 옆에\n내가 널 눈에\n꼭 담았을때\n너가 꼭 나를\n닮았다고 나는 생각을 했네\n그대로인 나를\n사랑해준다면 내 덧이\n조금 더 나을까\n사랑하는 너를 내맘속에\n품어 지낼순 없을까\n너가 지금 돌아가면 안되는\n이유들은 지금 많고 많지만\n너의 일상속에 내가 조금더\n스며들면해\n어느새 길어진\n나의 작은 곳 방안엔\n너의 좋은 향기가 아직\n공기에 맴도네\n내가 기억할 수 있다면\n너를 사랑할 수 있다면\n이제는 널위해\n그 손을 내가 꼭 잡을게\n비품속에 담아둔 너의\n마음을 전부 쓸어담어\n폐품처리했던 마음들을\n깨끗하게 다시 쓸어담어\n쓸데없던 고민 속에\n나는 생각을 했어\n니맘이 아직 ready라면\n나는 밟을 준비 됐어\n내일이 오면 끝은 다시 밝아지겠지\n버스 타는 중에도\n너가 계속 생각 나겠지\nNow we know yeah\nWe love it more yeah\n알던사이라도 조금 선을\n더더 넘어볼게\n데자부 꿈가 생긴\n나의 거리감\nIf you love me, you will alyways choose me\n한계절 한 시즌이\n갈때마다\nI fell your preciousness and empty space every day','JForest - 어느새.mp3','3d504bda-5a03-4450-ac65-0bd0718338d6.mp3','2022-10-03 00:00:00','2022-10-03 00:00:00','어느새',89),(107,'2022-10-06 14:26:32',NULL,'2022-10-06 17:03:09',NULL,'이명범','이명범의 첫 타이틀 곡','acoustic','crew_1665066267711.jpg','aff21389-469f-499f-b8d9-6a4628b4dc3c.jpg',4,'이명범','네가 참 궁금해 그건 너도 마찬가지\n이거면 충분해 쫓고 쫓는 이런 놀이\n참을 수 없는 이끌림과 호기심\n묘한 너와 나 두고 보면 알겠지','명범님 노래.mp3','29540ca0-9d3f-4eb5-8ff1-0c1db3664dfc.mp3','2019-10-06 00:00:00','2019-10-06 00:00:00','Love Dive',89),(109,'2022-10-06 15:00:16',NULL,'2022-10-06 15:00:16',NULL,'CHENDA','The beats are amazing and so calming','rock','crew_1665068214003.jpg','94a7d061-9a61-45d1-80d5-3b44228c9b8b.jpg',0,'CHENDA','Find Uou there','CHENDA - For You [NCS Release].mp3','8ccf4793-4d7e-44ae-afe3-a4ac8fdd8b51.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','For You',100),(110,'2022-10-06 15:03:56',NULL,'2022-10-06 15:03:56',NULL,'JOXION','Mysterious Energetic Music','edm','crew_1665068546905.jpg','d14e945b-ced9-4b47-9e63-674d4df1d7a5.jpg',0,'JOXION','All Access','JOXION - All Access [NCS Release].mp3','afda564c-52f0-4b7d-99fe-2e6c17f0e4de.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','All Access',100),(111,'2022-10-06 15:06:52',NULL,'2022-10-06 15:06:52',NULL,'Jim Yosef & Shiah maisel','Unforgivable','edm','crew_1665068721962.jpg','2a6b355d-5425-4354-9918-3eca9fabc97a.jpg',0,'Jim Yosef & Shiah maisel','Unforgivable','Jim Yosef & Shiah Maisel - Unforgivable [NCS Release].mp3','0cc927a6-8e14-4838-bafa-9ef91126ea50.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','Unforgivable',100),(112,'2022-10-06 15:09:24',NULL,'2022-10-06 15:09:24',NULL,'Max Brhon','NCS Release','edm','crew_1665068883884.jpg','9d0ad195-3ba6-4d46-ac6a-c7fc5efdd4a5.jpg',0,'Max Brhon','NCS Release','Max Brhon - Redemption [NCS Release].mp3','7342e451-1fc8-4ab7-926c-603b78a4c8a8.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','Redemption',100),(113,'2022-10-06 15:12:37',NULL,'2022-10-06 15:12:37',NULL,'Madzi & ETikka','Blind Heart','rnb','crew_1665069104559.jpg','f936dc92-4a94-43d5-abac-b53339db2dbe.jpg',0,'Madzi & ETikka','Blind Heart','MADZI & ETikka - Blind Heart [NCS Release].mp3','60596f8c-ec70-4a6f-be90-1cabbee0e283.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','Blind Heart',100),(114,'2022-10-06 15:14:46',NULL,'2022-10-06 15:14:46',NULL,'Soumix & Bromar','Falling For you','ballad','crew_1665069221421.jpg','ce077c02-fb5f-4cea-a8a1-a1847dbec159.jpg',0,'Soumix & Bromar','Lyric falling For you','SouMix & Bromar - Falling For You [NCS Release].mp3','4ac497f3-e4f8-4a2e-ad63-4b060c035bfe.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','Falling For You',100),(115,'2022-10-06 15:17:49',NULL,'2022-10-06 15:17:49',NULL,'Rapture & Sam Ourt','Taking control','ballad','crew_1665069408130.jpg','1e3aacf0-e26c-409c-8c56-86225779bfd8.jpg',0,'Rapture & Sam Ourt','If we try to believe\nIn anything greater than a miracle\nIt wouldn\'t be this difficult\nIf we, we try to be\nA negative pole, yeah\nWe could be taking control, yeah','_Raptures & Sam Ourt - Taking Control ft. Halvorsen (berx Remix) [NCS Release].mp3','c375e1d3-4fe5-41b1-92eb-c4ec76b96064.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','Taking control',100),(117,'2022-10-06 15:24:02',NULL,'2022-10-06 15:24:02',NULL,'ㅇㅇ','ㅇㅇ','edm','crew_1665069813367.jpg','b29f0dd0-44ee-4368-9d0d-9ac7969f4f65.jpg',0,'ㅇㅇ','ㅇㅇ','JForest - 어느새.mp3','7b64fc91-99d9-4dc8-babb-20d198d9712b.mp3','2022-10-07 00:00:00','2022-10-07 00:00:00','저메추',116),(120,'2022-10-06 15:28:05',NULL,'2022-10-06 15:28:05',NULL,'Richard Marx',' wating for you','ballad','crew_1665070020134.jpg','59ee90a4-ad5e-49ee-97b8-556d1c97eebf.jpg',0,'Richard Marx','I will be right here wating for you','Wating for You.mp3','55189f4c-f870-4158-84d9-879e2946e961.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','Wating for you',100),(123,'2022-10-06 15:40:43',NULL,'2022-10-06 15:40:43',NULL,'Paul Flint','E Welem Bah','rock','crew_1665070795220.jpg','9daedaee-b4a2-416a-96ef-4eb832dcf6da.jpg',0,'Paul Flint','E Welem Bah','_Paul Flint - E Welem Bah [NCS Release].mp3','f44f2e61-4aba-4191-81d3-1b0e11452d3f.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','E Welem Bah',122),(124,'2022-10-06 15:42:05',NULL,'2022-10-06 15:42:05',NULL,'Thorne','Call On me','dance','crew_1665070893755.jpg','509a7510-6b1c-4ad4-b7c6-2d392d047558.jpg',0,'Thorne','Call On Me','Thorne - Call On Me [NCS Release].mp3','8c23db3b-eab5-41fa-ad54-a62a1e2a3fea.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','Call On Me',122),(125,'2022-10-06 15:44:21',NULL,'2022-10-06 15:44:21',NULL,'Arcando','When I am With you','ballad','crew_1665071014333.jpg','e8f532ee-f3b6-4c90-ad82-f5d78863d559.jpg',0,'Arcando','When I am With you','_Arcando - When I\'m With You [NCS Release].mp3','d07b47d0-929c-4363-bf1f-801434a54d9f.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','When I\'m With You',122),(127,'2022-10-06 15:46:04',NULL,'2022-10-06 15:46:04',NULL,'Rapture & MAZAN','Sweet','ballad','crew_1665071121468.jpg','6474b309-c638-48c1-9bba-a24bbb10ff5a.jpg',0,'Rapture & MAZAN','Sweet','Raptures & MAZAN - Sweet [NCS Release].mp3','bb09c154-3597-432a-86da-3db0d89e44b4.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','Sweet',122),(130,'2022-10-06 15:47:40',NULL,'2022-10-06 15:47:40',NULL,'WBN & Mojnz','Radio','rnb','crew_1665071217299.jpg','7cba0d9b-84fa-4627-b17a-b9b2c23a723e.jpg',0,'WBN & Mojnz','Radio','WBN x Mojnz - Radio [NCS Release].mp3','6aa4afc9-5981-42cb-ae58-4bb31445b30e.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','Radio',122),(131,'2022-10-06 15:49:29',NULL,'2022-10-06 15:49:29',NULL,'Aeden','Would You Be Waiting','edm','crew_1665071315159.jpg','604c6d9b-6e10-463f-94ff-b2e8df476d94.jpg',0,'Aeden','Would You Be Waiting','Aeden - Would You Be Waiting [NCS Release].mp3','efa048b4-38e4-463e-9ea8-10ba5433a48e.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','Would You Be Waiting',122),(133,'2022-10-06 15:54:08',NULL,'2022-10-06 15:54:08',NULL,'Tobu','Memory Lane','new-age','crew_1665071613478.jpg','24af902b-7c66-4416-b6a6-8481aec4b842.jpg',0,'Tobu','Memory lane','Tobu - Memory Lane [NCS Release].mp3','58934533-9fdc-4ff9-9a84-fba28ec113c2.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','Memory Lane',132),(134,'2022-10-06 15:56:15',NULL,'2022-10-06 15:56:15',NULL,'CADMIUM & JAMZ ','Believe me','acoustic','crew_1665071712200.jpg','fe814121-c2f4-41d8-bcf1-06487d8d48da.jpg',0,'CADMIUM & JAMZ','Believe me','_CADMIUM X JAMZ X SIMONNE - Believe Me [NCS Release].mp3','152f71ad-e4b8-42dd-bfa9-39568fc369db.mp3','2022-10-06 00:00:00','2022-10-06 00:00:00','Believe Me',132);
/*!40000 ALTER TABLE `music` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-07  2:42:06
