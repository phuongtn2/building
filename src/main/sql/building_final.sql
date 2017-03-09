-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: building
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.19-MariaDB

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
-- Table structure for table `m_asset`
--

DROP TABLE IF EXISTS `m_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_asset` (
  `buildingCode` bigint(15) unsigned NOT NULL,
  `assetCode` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
  `assetType` tinyint(2) NOT NULL DEFAULT '0',
  `assetName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `dateFrom` datetime DEFAULT CURRENT_TIMESTAMP,
  `dateTo` datetime DEFAULT CURRENT_TIMESTAMP,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) DEFAULT '0',
  `updateId` int(11) DEFAULT '0',
  `price` float NOT NULL DEFAULT '0',
  `status` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`assetCode`),
  UNIQUE KEY `assetCode_UNIQUE` (`assetCode`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_asset`
--

LOCK TABLES `m_asset` WRITE;
/*!40000 ALTER TABLE `m_asset` DISABLE KEYS */;
INSERT INTO `m_asset` VALUES (1500,1,1,'Hồ bơi','2016-12-07 00:00:00','2016-12-30 00:00:00','2016-12-03 16:00:12','2016-12-03 16:00:12',0,0,0,0),(30000,2,2,'Máy tập chạy','2016-09-05 00:00:00','2016-12-29 00:00:00','2016-12-03 16:00:32','2016-12-03 16:00:32',0,0,0,0);
/*!40000 ALTER TABLE `m_asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_building`
--

DROP TABLE IF EXISTS `m_building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_building` (
  `buildingCode` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
  `buildingName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `totalFloor` int(11) DEFAULT '0',
  `totalRoom` int(11) DEFAULT '0',
  `description` longtext COLLATE utf8_unicode_ci,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) DEFAULT '0',
  `updateId` int(11) DEFAULT '0',
  PRIMARY KEY (`buildingCode`),
  UNIQUE KEY `buildingCode_UNIQUE` (`buildingCode`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_building`
--

LOCK TABLES `m_building` WRITE;
/*!40000 ALTER TABLE `m_building` DISABLE KEYS */;
INSERT INTO `m_building` VALUES (7,'building 4',12,12,'wwwwwww','2016-12-05 21:38:38','2017-01-10 21:33:27',0,NULL);
/*!40000 ALTER TABLE `m_building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_complaint`
--

DROP TABLE IF EXISTS `m_complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_complaint` (
  `complaintCode` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) DEFAULT '0',
  `updateId` int(11) DEFAULT '0',
  `followStatus` tinyint(2) NOT NULL DEFAULT '0',
  `serviceCode` bigint(15) NOT NULL DEFAULT '0',
  `title` longtext COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`complaintCode`),
  UNIQUE KEY `complaintCode_UNIQUE` (`complaintCode`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_complaint`
--

LOCK TABLES `m_complaint` WRITE;
/*!40000 ALTER TABLE `m_complaint` DISABLE KEYS */;
INSERT INTO `m_complaint` VALUES (1,'2016-11-28 22:06:29','2016-12-13 21:45:52',0,0,0,0,'kkkkkkkkkkkkkkkkkkkkkkkkkkkgggg'),(2,'2016-11-28 22:14:41','2016-11-28 22:14:41',0,0,0,1,'22222222222'),(3,'2016-11-29 18:08:35','2016-11-29 18:08:39',1,0,1,2,'121212'),(4,'2016-11-29 20:41:13','2016-12-06 13:11:24',1,0,1,1,'1111111111'),(5,'2016-12-06 13:09:15','2016-12-06 13:09:15',1,0,0,4,'san ban');
/*!40000 ALTER TABLE `m_complaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_floor`
--

DROP TABLE IF EXISTS `m_floor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_floor` (
  `floorCode` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
  `floorSeq` int(11) NOT NULL DEFAULT '0',
  `floorAlias` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `floorType` tinyint(2) NOT NULL DEFAULT '0',
  `buildingCode` bigint(15) NOT NULL,
  `totalRoom` int(11) DEFAULT '0',
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) DEFAULT '0',
  `updateId` int(11) DEFAULT '0',
  PRIMARY KEY (`floorCode`),
  UNIQUE KEY `floorCode_UNIQUE` (`floorCode`),
  KEY `fk_m_building_idx` (`buildingCode`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_floor`
--

LOCK TABLES `m_floor` WRITE;
/*!40000 ALTER TABLE `m_floor` DISABLE KEYS */;
INSERT INTO `m_floor` VALUES (6,1,'ww',1,5,12,'2016-11-25 23:15:53','2016-11-25 23:15:53',1,1),(15,1,'1',2,0,1,'2016-11-26 16:47:00','2016-11-26 16:47:00',1,1),(16,22,'54',2,5,19,'2016-11-26 21:07:02','2016-11-26 21:07:02',1,1),(17,12,'tầng 12',1,7,3,'2017-01-05 20:54:12','2017-01-05 20:54:12',1,1),(18,1,'tầng 1',1,7,12,'2017-01-10 21:33:51','2017-01-10 21:33:51',1,1);
/*!40000 ALTER TABLE `m_floor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_news`
--

DROP TABLE IF EXISTS `m_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_news` (
  `newsCode` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
  `newsType` tinyint(2) NOT NULL DEFAULT '0',
  `newsHeader` varchar(255) CHARACTER SET utf8 NOT NULL,
  `newsShorter` varchar(255) CHARACTER SET utf8 NOT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) DEFAULT '0',
  `updateId` int(11) DEFAULT '0',
  PRIMARY KEY (`newsCode`),
  UNIQUE KEY `newsCode_UNIQUE` (`newsCode`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_news`
--

LOCK TABLES `m_news` WRITE;
/*!40000 ALTER TABLE `m_news` DISABLE KEYS */;
INSERT INTO `m_news` VALUES (1,1,'Điểm lại 7 trận chiến giữa tuyển Việt Nam và Indonesia tại AFF Cup','Tuyển Việt Nam và Indonesia từng bảy lần gặp nhau tại đấu trường AFF Cup và kết quả hòa diễn ra khá nhiều. Có tới bốn trận hòa giữa hai bên, đội tuyển Việt Nam thắng được một trận và Indonesia thắng được hai trận.','2016-12-03 15:46:56','2016-12-03 15:46:56',0,0),(2,2,'10 bàn thắng đẹp nhất vòng bảng AFF Cup 2016','AFF vừa công bố nhóm 10 bàn thắng đẹp nhất vòng bảng của giải vô địch Đông Nam Á năm nay. Đáng chú ý không có bàn thắng nào trong số này được ghi bởi các chân sút Việt Nam. Ngược lại, Campuchia gây bất ngờ khi có tới 2 đại diện.','2016-12-03 15:47:57','2016-12-03 15:47:57',0,0);
/*!40000 ALTER TABLE `m_news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_room`
--

DROP TABLE IF EXISTS `m_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_room` (
  `roomCode` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
  `roomAlias` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `floorCode` bigint(15) unsigned NOT NULL,
  `count` int(11) DEFAULT '0',
  `status` tinyint(2) NOT NULL DEFAULT '0',
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) DEFAULT '0',
  `updateId` int(11) DEFAULT '0',
  PRIMARY KEY (`roomCode`),
  UNIQUE KEY `roomCode_UNIQUE` (`roomCode`),
  KEY `fk_m_floor_idx` (`floorCode`),
  CONSTRAINT `fk_m_floor` FOREIGN KEY (`floorCode`) REFERENCES `m_floor` (`floorCode`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_room`
--

LOCK TABLES `m_room` WRITE;
/*!40000 ALTER TABLE `m_room` DISABLE KEYS */;
INSERT INTO `m_room` VALUES (1,'1',17,3,1,'2017-01-05 20:55:00','2017-01-05 20:55:00',1,1),(2,'phòng 12',18,12,1,'2017-01-10 21:34:12','2017-01-10 21:34:12',1,1);
/*!40000 ALTER TABLE `m_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_service`
--

DROP TABLE IF EXISTS `m_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_service` (
  `serviceCode` bigint(15) unsigned NOT NULL,
  `buildingCode` bigint(15) unsigned NOT NULL,
  `serviceType` tinyint(2) NOT NULL DEFAULT '0',
  `serviceName` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `serviceStart` datetime DEFAULT NULL,
  `serviceEnd` datetime DEFAULT CURRENT_TIMESTAMP,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) DEFAULT '0',
  `updateId` int(11) DEFAULT '0',
  PRIMARY KEY (`serviceCode`),
  UNIQUE KEY `serviceCode_UNIQUE` (`serviceCode`),
  KEY `service_building_idx` (`buildingCode`),
  CONSTRAINT `a` FOREIGN KEY (`buildingCode`) REFERENCES `m_building` (`buildingCode`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_service`
--

LOCK TABLES `m_service` WRITE;
/*!40000 ALTER TABLE `m_service` DISABLE KEYS */;
INSERT INTO `m_service` VALUES (2,7,2,'Phòng họp','2016-11-23 00:00:00','2016-11-23 00:00:00','2016-11-23 21:49:02','2016-12-21 20:26:07',0,0),(3,7,1,'Sân tenis','2016-11-23 00:00:00','2016-11-23 00:00:00','2016-11-23 22:13:15','2016-12-21 20:25:46',0,0),(4,7,3,'Sân bóng','0018-05-09 00:00:00','0018-05-09 00:00:00','2016-11-23 22:22:22','2016-12-21 20:25:26',0,0),(5,7,1,'Phòng gym','0005-10-06 00:00:00','0005-10-06 00:00:00','2016-11-23 22:55:09','2016-12-21 20:25:02',0,0),(6,7,1,'Hồ bơi','2016-11-11 00:00:00','2016-11-17 00:00:00','2016-11-28 20:51:47','2016-12-21 20:24:52',0,0);
/*!40000 ALTER TABLE `m_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_asset`
--

DROP TABLE IF EXISTS `t_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_asset` (
  `assetCode` bigint(15) NOT NULL,
  `userId` int(11) NOT NULL,
  `tranSeq` int(11) NOT NULL,
  `message` longtext COLLATE utf8_unicode_ci,
  `followStatus` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`assetCode`,`userId`,`tranSeq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_asset`
--

LOCK TABLES `t_asset` WRITE;
/*!40000 ALTER TABLE `t_asset` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_booking_service`
--

DROP TABLE IF EXISTS `t_booking_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_booking_service` (
  `bookServiceCode` bigint(15) NOT NULL AUTO_INCREMENT,
  `totalPrice` double NOT NULL,
  `userId` int(11) unsigned NOT NULL,
  `bookFrom` datetime DEFAULT CURRENT_TIMESTAMP,
  `bookTo` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(2) NOT NULL DEFAULT '0',
  `followStatus` tinyint(2) NOT NULL DEFAULT '0',
  `created` datetime DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `createId` int(11) DEFAULT NULL,
  `updateId` int(11) DEFAULT NULL,
  `memo` longtext COLLATE utf8_unicode_ci,
  PRIMARY KEY (`bookServiceCode`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_booking_service`
--

LOCK TABLES `t_booking_service` WRITE;
/*!40000 ALTER TABLE `t_booking_service` DISABLE KEYS */;
INSERT INTO `t_booking_service` VALUES (1,6666,1,'2017-01-05 00:00:00','2017-01-05 12:00:00',3,0,'2016-11-29 22:59:00','2016-11-29 23:10:24',0,0,'aaaa');
/*!40000 ALTER TABLE `t_booking_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_booking_service_group`
--

DROP TABLE IF EXISTS `t_booking_service_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_booking_service_group` (
  `bookingServiceCode` bigint(15) NOT NULL,
  `serviceCode` bigint(15) NOT NULL,
  PRIMARY KEY (`bookingServiceCode`,`serviceCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_booking_service_group`
--

LOCK TABLES `t_booking_service_group` WRITE;
/*!40000 ALTER TABLE `t_booking_service_group` DISABLE KEYS */;
INSERT INTO `t_booking_service_group` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `t_booking_service_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_complaint`
--

DROP TABLE IF EXISTS `t_complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_complaint` (
  `tComplaintCode` bigint(15) NOT NULL AUTO_INCREMENT,
  `complaintCode` bigint(15) NOT NULL,
  `userId` int(11) NOT NULL,
  `tranSeq` tinyint(2) NOT NULL,
  `message` longtext COLLATE utf8_unicode_ci,
  `parentComplaintCode` bigint(15) NOT NULL DEFAULT '0',
  `userName` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`tComplaintCode`,`complaintCode`,`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_complaint`
--

LOCK TABLES `t_complaint` WRITE;
/*!40000 ALTER TABLE `t_complaint` DISABLE KEYS */;
INSERT INTO `t_complaint` VALUES (1,1,1,1,'111',1,''),(2,1,1,2,'222',1,''),(3,1,1,2,'tutyt',1,'Tran Ngoc Phuong'),(4,1,1,1,'rhjgfjg',3,'Tran Ngoc Phuong'),(5,1,1,2,'ddsfdsgd',1,'Tran Ngoc Phuong'),(6,1,1,2,'jjj',1,'Tran Ngoc Phuong'),(7,1,1,2,'j',1,'Tran Ngoc Phuong'),(8,1,1,2,'cầu thang bẩn',3,'Tran Ngoc Phuong'),(9,1,1,2,'hfjhd',1,'Tran Ngoc Phuong'),(10,1,1,2,'jjhhhf',1,'Tran Ngoc Phuong'),(11,1,1,2,'fffff',1,'Tran Ngoc Phuong'),(12,1,1,2,'hfhff',1,'Tran Ngoc Phuong'),(13,1,1,2,'\njkjsf',1,'Tran Ngoc Phuong'),(14,1,1,2,'kjkjgdkfgf',1,'Tran Ngoc Phuong'),(15,1,1,2,'fgkflkgflkgflg',1,'Tran Ngoc Phuong');
/*!40000 ALTER TABLE `t_complaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_news_detail`
--

DROP TABLE IF EXISTS `t_news_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_news_detail` (
  `newsDetailCode` bigint(15) NOT NULL AUTO_INCREMENT,
  `refNewsCode` bigint(15) DEFAULT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) DEFAULT '0',
  `updateId` int(11) DEFAULT '0',
  `newsCode` bigint(15) NOT NULL DEFAULT '0',
  PRIMARY KEY (`newsDetailCode`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_news_detail`
--

LOCK TABLES `t_news_detail` WRITE;
/*!40000 ALTER TABLE `t_news_detail` DISABLE KEYS */;
INSERT INTO `t_news_detail` VALUES (1,2,'2017-03-02 20:04:27','2017-03-02 20:04:27',0,0,1);
/*!40000 ALTER TABLE `t_news_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_role` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(250) NOT NULL,
  `roleMemo` varchar(250) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) NOT NULL DEFAULT '0',
  `updateId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`roleId`),
  UNIQUE KEY `roleId` (`roleId`),
  UNIQUE KEY `roleName` (`roleName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role`
--

LOCK TABLES `t_role` WRITE;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` VALUES (1,'ADMIN','ADMIN','2016-11-03 19:33:25','2016-11-03 19:33:25',0,0),(2,'MEMBER','MEMBER','2016-11-03 19:33:25','2016-11-03 19:33:25',0,0),(3,'WEB','WEB','2016-11-03 19:33:25','2016-11-03 19:33:25',0,0),(4,'MOBILE','MOBILE','2016-11-03 19:33:25','2016-11-03 19:33:25',0,0);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_rolegroup`
--

DROP TABLE IF EXISTS `t_rolegroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_rolegroup` (
  `roleGroupId` int(11) NOT NULL AUTO_INCREMENT,
  `roleGroupName` varchar(250) NOT NULL,
  `roleGroupMemo` varchar(250) DEFAULT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) NOT NULL DEFAULT '0',
  `updateId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`roleGroupId`),
  UNIQUE KEY `roleGroupId` (`roleGroupId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_rolegroup`
--

LOCK TABLES `t_rolegroup` WRITE;
/*!40000 ALTER TABLE `t_rolegroup` DISABLE KEYS */;
INSERT INTO `t_rolegroup` VALUES (1,'ADMIN','ADMIN','2016-11-03 19:35:22','2016-11-03 19:35:22',0,0),(2,'MEMBER','MEMBER','2016-11-03 19:35:22','2016-11-03 19:35:22',0,0),(3,'WEB','WEB','2016-11-03 19:36:09','2016-11-03 19:36:09',0,0),(4,'MOBILE','MOBILE','2016-11-03 19:36:09','2016-11-03 19:36:09',0,0);
/*!40000 ALTER TABLE `t_rolegroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_rolegrouprole`
--

DROP TABLE IF EXISTS `t_rolegrouprole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_rolegrouprole` (
  `roleGroupRoleId` int(11) NOT NULL AUTO_INCREMENT,
  `roleGroupId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) NOT NULL DEFAULT '0',
  `updateId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`roleGroupRoleId`),
  UNIQUE KEY `roleGroupRoleId` (`roleGroupRoleId`),
  KEY `roleId` (`roleId`),
  KEY `roleGroupId` (`roleGroupId`),
  CONSTRAINT `T_ROLEGROUPROLE_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`roleId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `T_ROLEGROUPROLE_ibfk_2` FOREIGN KEY (`roleGroupId`) REFERENCES `t_rolegroup` (`roleGroupId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_rolegrouprole`
--

LOCK TABLES `t_rolegrouprole` WRITE;
/*!40000 ALTER TABLE `t_rolegrouprole` DISABLE KEYS */;
INSERT INTO `t_rolegrouprole` VALUES (1,1,1,'2016-11-03 19:42:05','2016-11-03 19:42:05',0,0),(2,1,2,'2016-11-03 19:42:05','2016-11-03 19:42:05',0,0),(3,2,2,'2016-11-03 19:42:05','2016-11-03 19:42:05',0,0),(4,1,4,'2016-11-03 19:42:05','2016-11-03 19:42:05',0,0);
/*!40000 ALTER TABLE `t_rolegrouprole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_service_asset`
--

DROP TABLE IF EXISTS `t_service_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_service_asset` (
  `serviceCode` bigint(15) NOT NULL,
  `assetCode` bigint(15) NOT NULL,
  PRIMARY KEY (`serviceCode`,`assetCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_service_asset`
--

LOCK TABLES `t_service_asset` WRITE;
/*!40000 ALTER TABLE `t_service_asset` DISABLE KEYS */;
INSERT INTO `t_service_asset` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `t_service_asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `adId` varchar(30) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(45) CHARACTER SET utf8 NOT NULL,
  `userStatus` tinyint(4) DEFAULT NULL,
  `fullName` varchar(255) CHARACTER SET utf8 NOT NULL,
  `mail` varchar(255) CHARACTER SET utf8 NOT NULL,
  `tel` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `startDay` datetime DEFAULT NULL,
  `endDay` datetime DEFAULT NULL,
  `hideFlg` tinyint(4) DEFAULT '0',
  `gender` tinyint(2) DEFAULT NULL,
  `memo` text CHARACTER SET utf8,
  `address` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `logo` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) NOT NULL DEFAULT '0',
  `updateId` int(11) NOT NULL DEFAULT '0',
  `idCard` int(12) DEFAULT '0',
  `birthday` datetime DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'test','123456',1,'Tran Ngoc Phuong','chuchuot12a15tnp@gmail.com','0976408409',NULL,NULL,NULL,1,NULL,'abc',NULL,'2016-11-03 19:37:40','2017-01-04 22:17:06',0,1,NULL,NULL),(2,'member','123456',2,'Tran Ngoc Phuong','chuchuot12a15tnp@gmail.com','0976408409',NULL,NULL,NULL,2,NULL,'xyz1212',NULL,'2016-11-03 19:37:40','2016-12-05 23:53:16',0,0,1234321,'2016-12-06 00:00:00');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_room`
--

DROP TABLE IF EXISTS `t_user_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_room` (
  `userRoomId` bigint(15) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `roomCode` bigint(15) DEFAULT NULL,
  `floorCode` bigint(15) DEFAULT NULL,
  `buildingCode` bigint(15) DEFAULT NULL,
  `startDay` date DEFAULT NULL,
  `endDay` date DEFAULT NULL,
  PRIMARY KEY (`userRoomId`),
  UNIQUE KEY `userId_UNIQUE` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_room`
--

LOCK TABLES `t_user_room` WRITE;
/*!40000 ALTER TABLE `t_user_room` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_userrolegroup`
--

DROP TABLE IF EXISTS `t_userrolegroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_userrolegroup` (
  `userRoleGroupId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `startDay` date DEFAULT '0000-00-00',
  `endDay` date DEFAULT '9999-12-31',
  `roleGroupId` int(11) NOT NULL,
  `memo` varchar(250) DEFAULT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) NOT NULL DEFAULT '0',
  `updateId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userRoleGroupId`),
  UNIQUE KEY `userRoleGroupId` (`userRoleGroupId`),
  UNIQUE KEY `userId` (`userId`),
  KEY `roleGroupId` (`roleGroupId`),
  CONSTRAINT `T_USERROLEGROUP_ibfk_2` FOREIGN KEY (`roleGroupId`) REFERENCES `t_rolegroup` (`roleGroupId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `T_USERROLEGROUP_ibfk_3` FOREIGN KEY (`userId`) REFERENCES `t_user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_userrolegroup`
--

LOCK TABLES `t_userrolegroup` WRITE;
/*!40000 ALTER TABLE `t_userrolegroup` DISABLE KEYS */;
INSERT INTO `t_userrolegroup` VALUES (1,1,'2016-11-03','2016-11-03',1,NULL,'2016-11-03 19:40:28','2016-11-03 19:40:28',0,0),(2,2,'2016-11-03','2016-11-03',2,NULL,'2016-11-03 19:40:28','2016-11-03 19:40:28',0,0);
/*!40000 ALTER TABLE `t_userrolegroup` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-02 20:37:14
