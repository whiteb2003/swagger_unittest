-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: security
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'USER'),(2,'CREATOR'),(3,'EDITOR'),(4,'ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (_binary '',1,'$2a$10$CyefWJgOyIocTjMqxjgi7esrJx7lEH/oD3baqwWNk.ZGUucbjngnC','patrick',NULL),(_binary '',2,'$2a$10$CyefWJgOyIocTjMqxjgi7esrJx7lEH/oD3baqwWNk.ZGUucbjngnC','alex',NULL),(_binary '',3,'$2a$10$CyefWJgOyIocTjMqxjgi7esrJx7lEH/oD3baqwWNk.ZGUucbjngnC','john',NULL),(_binary '',4,'$2a$10$CyefWJgOyIocTjMqxjgi7esrJx7lEH/oD3baqwWNk.ZGUucbjngnC','namhm',NULL),(_binary '',5,'$2a$10$CyefWJgOyIocTjMqxjgi7esrJx7lEH/oD3baqwWNk.ZGUucbjngnC','admin',NULL),(_binary '',6,'$2a$10$CyefWJgOyIocTjMqxjgi7esrJx7lEH/oD3baqwWNk.ZGUucbjngnC','trung',NULL),(_binary '',7,'$2a$10$CyefWJgOyIocTjMqxjgi7esrJx7lEH/oD3baqwWNk.ZGUucbjngnC','hai',NULL),(_binary '',8,'$2a$10$K9nkWimHt.89l.vZsmhjuO4ZhaeT0233BqF8PtZcrqoUuGDQ4QdRq','bach',NULL),(_binary '\0',9,'$2a$10$K9nkWimHt.89l.vZsmhjuO4ZhaeT0233BqF8PtZcrqoUuGDQ4QdRq','thai',NULL),(_binary '\0',10,'$2a$10$TZPCL6eBRfi9LTJth.jzTeWV9pGJ5EyXkPMoJXoy9yDpslp/tT8Ri','trinh','W6S2Kybz-W7OUdvQa2BJpgi0VHbs7M1_A9yHL5PVvXc4uS4kIqQOXtI4xMVrbMamHpjGhewC559ruvK2PEtl1Q'),(_binary '',15,'$2a$10$ICKOTNcEoaxI/NAf5xJPPOkWXlZYhoQxTqBV2h3Mt3ajs4qdj8ldC','hoanghaido2003@gmail.com','BNSutyVpF9HUBzROggBHrg7BQnMxWtn5xtB_Ng3dxOXbVR3nugVtkOtfN0uJ7M01GHZOiwozvmUxXqNN5t2r8g'),(_binary '',17,'$2a$10$flYGgWYfeF4YkW3Wjqn3sebnbBMXzf2BBfdB9zBoSwVHG28isnXDa','dang',NULL),(_binary '',23,'$2a$10$QBWYSaMebaKVCDQqAhvGY.uQtp4rLgPtxCgjmUIuBs2BqnOmltqgK','good',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(2,2),(3,3),(2,4),(3,4),(4,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,15);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-16 15:59:38
