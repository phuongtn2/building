/*
SQLyog Community v12.09 (64 bit)
MySQL - 10.1.10-MariaDB : Database - building
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`building` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `building`;

/*Table structure for table `m_asset` */

DROP TABLE IF EXISTS `m_asset`;

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
  PRIMARY KEY (`assetCode`),
  UNIQUE KEY `assetCode_UNIQUE` (`assetCode`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `m_asset` */

insert  into `m_asset`(`buildingCode`,`assetCode`,`assetType`,`assetName`,`dateFrom`,`dateTo`,`created`,`lastUpdate`,`createId`,`updateId`,`price`) values (1500,1,1,'Hồ bơi','2016-12-07 00:00:00','2016-12-30 00:00:00','2016-12-03 16:00:12','2016-12-03 16:00:12',0,0,0),(30000,2,2,'Máy tập chạy','2016-09-05 00:00:00','2016-12-29 00:00:00','2016-12-03 16:00:32','2016-12-03 16:00:32',0,0,0);

/*Table structure for table `m_building` */

DROP TABLE IF EXISTS `m_building`;

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

/*Data for the table `m_building` */

insert  into `m_building`(`buildingCode`,`buildingName`,`totalFloor`,`totalRoom`,`description`,`created`,`lastUpdate`,`createId`,`updateId`) values (7,'building 4',12,12,'wwwwwww','2016-12-05 21:38:38','2017-01-10 21:33:27',0,NULL);

/*Table structure for table `m_complaint` */

DROP TABLE IF EXISTS `m_complaint`;

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

/*Data for the table `m_complaint` */

insert  into `m_complaint`(`complaintCode`,`created`,`lastUpdate`,`createId`,`updateId`,`followStatus`,`serviceCode`,`title`) values (1,'2016-11-28 22:06:29','2016-12-13 21:45:52',0,0,0,0,'kkkkkkkkkkkkkkkkkkkkkkkkkkkgggg'),(2,'2016-11-28 22:14:41','2016-11-28 22:14:41',0,0,0,1,'22222222222'),(3,'2016-11-29 18:08:35','2016-11-29 18:08:39',1,0,1,2,'121212'),(4,'2016-11-29 20:41:13','2016-12-06 13:11:24',1,0,1,1,'1111111111'),(5,'2016-12-06 13:09:15','2016-12-06 13:09:15',1,0,0,4,'san ban');

/*Table structure for table `m_floor` */

DROP TABLE IF EXISTS `m_floor`;

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

/*Data for the table `m_floor` */

insert  into `m_floor`(`floorCode`,`floorSeq`,`floorAlias`,`floorType`,`buildingCode`,`totalRoom`,`created`,`lastUpdate`,`createId`,`updateId`) values (6,1,'ww',1,5,12,'2016-11-25 23:15:53','2016-11-25 23:15:53',1,1),(15,1,'1',2,0,1,'2016-11-26 16:47:00','2016-11-26 16:47:00',1,1),(16,22,'54',2,5,19,'2016-11-26 21:07:02','2016-11-26 21:07:02',1,1),(17,12,'tầng 12',1,7,3,'2017-01-05 20:54:12','2017-01-05 20:54:12',1,1),(18,1,'tầng 1',1,7,12,'2017-01-10 21:33:51','2017-01-10 21:33:51',1,1);

/*Table structure for table `m_news` */

DROP TABLE IF EXISTS `m_news`;

CREATE TABLE `m_news` (
  `newCode` bigint(15) unsigned NOT NULL AUTO_INCREMENT,
  `newType` tinyint(2) NOT NULL DEFAULT '0',
  `newHeader` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `newShorter` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `refNewCode` bigint(15) DEFAULT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) DEFAULT '0',
  `updateId` int(11) DEFAULT '0',
  PRIMARY KEY (`newCode`),
  UNIQUE KEY `newCode_UNIQUE` (`newCode`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `m_news` */

insert  into `m_news`(`newCode`,`newType`,`newHeader`,`newShorter`,`refNewCode`,`created`,`lastUpdate`,`createId`,`updateId`) values (12,1,'Điểm lại 7 trận chiến giữa tuyển Việt Nam và Indonesia tại AFF Cup','Tuyển Việt Nam và Indonesia từng bảy lần gặp nhau tại đấu trường AFF Cup và kết quả hòa diễn ra khá nhiều. Có tới bốn trận hòa giữa hai bên, đội tuyển Việt Nam thắng được một trận và Indonesia thắng được hai trận.',1,'2016-12-03 15:46:56','2016-12-03 15:46:56',0,0),(13,2,'10 bàn thắng đẹp nhất vòng bảng AFF Cup 2016','AFF vừa công bố nhóm 10 bàn thắng đẹp nhất vòng bảng của giải vô địch Đông Nam Á năm nay. Đáng chú ý không có bàn thắng nào trong số này được ghi bởi các chân sút Việt Nam. Ngược lại, Campuchia gây bất ngờ khi có tới 2 đại diện.',12,'2016-12-03 15:47:57','2016-12-03 15:47:57',0,0);

/*Table structure for table `m_room` */

DROP TABLE IF EXISTS `m_room`;

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

/*Data for the table `m_room` */

insert  into `m_room`(`roomCode`,`roomAlias`,`floorCode`,`count`,`status`,`created`,`lastUpdate`,`createId`,`updateId`) values (1,'1',17,3,1,'2017-01-05 20:55:00','2017-01-05 20:55:00',1,1),(2,'phòng 12',18,12,1,'2017-01-10 21:34:12','2017-01-10 21:34:12',1,1);

/*Table structure for table `m_service` */

DROP TABLE IF EXISTS `m_service`;

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

/*Data for the table `m_service` */

insert  into `m_service`(`serviceCode`,`buildingCode`,`serviceType`,`serviceName`,`serviceStart`,`serviceEnd`,`created`,`lastUpdate`,`createId`,`updateId`) values (2,7,2,'Phòng họp','2016-11-23 00:00:00','2016-11-23 00:00:00','2016-11-23 21:49:02','2016-12-21 20:26:07',0,0),(3,7,1,'Sân tenis','2016-11-23 00:00:00','2016-11-23 00:00:00','2016-11-23 22:13:15','2016-12-21 20:25:46',0,0),(4,7,3,'Sân bóng','0018-05-09 00:00:00','0018-05-09 00:00:00','2016-11-23 22:22:22','2016-12-21 20:25:26',0,0),(5,7,1,'Phòng gym','0005-10-06 00:00:00','0005-10-06 00:00:00','2016-11-23 22:55:09','2016-12-21 20:25:02',0,0),(6,7,1,'Hồ bơi','2016-11-11 00:00:00','2016-11-17 00:00:00','2016-11-28 20:51:47','2016-12-21 20:24:52',0,0);

/*Table structure for table `t_asset` */

DROP TABLE IF EXISTS `t_asset`;

CREATE TABLE `t_asset` (
  `assetCode` bigint(15) NOT NULL,
  `userId` int(11) NOT NULL,
  `tranSeq` int(11) NOT NULL,
  `message` longtext COLLATE utf8_unicode_ci,
  `followStatus` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`assetCode`,`userId`,`tranSeq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_asset` */

/*Table structure for table `t_book_service` */

DROP TABLE IF EXISTS `t_book_service`;

CREATE TABLE `t_book_service` (
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

/*Data for the table `t_book_service` */

insert  into `t_book_service`(`bookServiceCode`,`totalPrice`,`userId`,`bookFrom`,`bookTo`,`status`,`followStatus`,`created`,`lastUpdate`,`createId`,`updateId`,`memo`) values (1,6666,1,'2017-01-05 00:00:00','2017-01-05 12:00:00',3,0,'2016-11-29 22:59:00','2016-11-29 23:10:24',0,0,'aaaa');

/*Table structure for table `t_booking_service_group` */

DROP TABLE IF EXISTS `t_booking_service_group`;

CREATE TABLE `t_booking_service_group` (
  `bookingServiceCode` bigint(15) NOT NULL,
  `serviceCode` bigint(15) NOT NULL,
  PRIMARY KEY (`bookingServiceCode`,`serviceCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_booking_service_group` */

insert  into `t_booking_service_group`(`bookingServiceCode`,`serviceCode`) values (1,1),(1,2);

/*Table structure for table `t_complaint` */

DROP TABLE IF EXISTS `t_complaint`;

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

/*Data for the table `t_complaint` */

insert  into `t_complaint`(`tComplaintCode`,`complaintCode`,`userId`,`tranSeq`,`message`,`parentComplaintCode`,`userName`) values (1,1,1,1,'111',1,''),(2,1,1,2,'222',1,''),(3,1,1,2,'tutyt',1,'Tran Ngoc Phuong'),(4,1,1,1,'rhjgfjg',3,'Tran Ngoc Phuong'),(5,1,1,2,'ddsfdsgd',1,'Tran Ngoc Phuong'),(6,1,1,2,'jjj',1,'Tran Ngoc Phuong'),(7,1,1,2,'j',1,'Tran Ngoc Phuong'),(8,1,1,2,'cầu thang bẩn',3,'Tran Ngoc Phuong'),(9,1,1,2,'hfjhd',1,'Tran Ngoc Phuong'),(10,1,1,2,'jjhhhf',1,'Tran Ngoc Phuong'),(11,1,1,2,'fffff',1,'Tran Ngoc Phuong'),(12,1,1,2,'hfhff',1,'Tran Ngoc Phuong'),(13,1,1,2,'\njkjsf',1,'Tran Ngoc Phuong'),(14,1,1,2,'kjkjgdkfgf',1,'Tran Ngoc Phuong'),(15,1,1,2,'fgkflkgflkgflg',1,'Tran Ngoc Phuong');

/*Table structure for table `t_new_detail` */

DROP TABLE IF EXISTS `t_new_detail`;

CREATE TABLE `t_new_detail` (
  `newDetailCode` bigint(15) NOT NULL AUTO_INCREMENT,
  `newCode` bigint(15) unsigned NOT NULL,
  `newContent` longtext COLLATE utf8_unicode_ci NOT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `lastUpdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `createId` int(11) DEFAULT '0',
  `updateId` int(11) DEFAULT '0',
  PRIMARY KEY (`newDetailCode`),
  KEY `fk_m_news_idx` (`newCode`),
  CONSTRAINT `fk_m_news` FOREIGN KEY (`newCode`) REFERENCES `m_news` (`newCode`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_new_detail` */

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

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

/*Data for the table `t_role` */

insert  into `t_role`(`roleId`,`roleName`,`roleMemo`,`created`,`lastUpdate`,`createId`,`updateId`) values (1,'ADMIN','ADMIN','2016-11-03 19:33:25','2016-11-03 19:33:25',0,0),(2,'MEMBER','MEMBER','2016-11-03 19:33:25','2016-11-03 19:33:25',0,0),(3,'WEB','WEB','2016-11-03 19:33:25','2016-11-03 19:33:25',0,0),(4,'MOBILE','MOBILE','2016-11-03 19:33:25','2016-11-03 19:33:25',0,0);

/*Table structure for table `t_rolegroup` */

DROP TABLE IF EXISTS `t_rolegroup`;

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

/*Data for the table `t_rolegroup` */

insert  into `t_rolegroup`(`roleGroupId`,`roleGroupName`,`roleGroupMemo`,`created`,`lastUpdate`,`createId`,`updateId`) values (1,'ADMIN','ADMIN','2016-11-03 19:35:22','2016-11-03 19:35:22',0,0),(2,'MEMBER','MEMBER','2016-11-03 19:35:22','2016-11-03 19:35:22',0,0),(3,'WEB','WEB','2016-11-03 19:36:09','2016-11-03 19:36:09',0,0),(4,'MOBILE','MOBILE','2016-11-03 19:36:09','2016-11-03 19:36:09',0,0);

/*Table structure for table `t_rolegrouprole` */

DROP TABLE IF EXISTS `t_rolegrouprole`;

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

/*Data for the table `t_rolegrouprole` */

insert  into `t_rolegrouprole`(`roleGroupRoleId`,`roleGroupId`,`roleId`,`created`,`lastUpdate`,`createId`,`updateId`) values (1,1,1,'2016-11-03 19:42:05','2016-11-03 19:42:05',0,0),(2,1,2,'2016-11-03 19:42:05','2016-11-03 19:42:05',0,0),(3,2,2,'2016-11-03 19:42:05','2016-11-03 19:42:05',0,0),(4,1,4,'2016-11-03 19:42:05','2016-11-03 19:42:05',0,0);

/*Table structure for table `t_service_asset` */

DROP TABLE IF EXISTS `t_service_asset`;

CREATE TABLE `t_service_asset` (
  `serviceCode` bigint(15) NOT NULL,
  `assetCode` bigint(15) NOT NULL,
  PRIMARY KEY (`serviceCode`,`assetCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_service_asset` */

insert  into `t_service_asset`(`serviceCode`,`assetCode`) values (1,1),(1,2);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

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

/*Data for the table `t_user` */

insert  into `t_user`(`userId`,`adId`,`password`,`userStatus`,`fullName`,`mail`,`tel`,`startDay`,`endDay`,`hideFlg`,`gender`,`memo`,`address`,`logo`,`created`,`lastUpdate`,`createId`,`updateId`,`idCard`,`birthday`) values (1,'test','123456',1,'Tran Ngoc Phuong','chuchuot12a15tnp@gmail.com','0976408409',NULL,NULL,NULL,1,NULL,'abc',NULL,'2016-11-03 19:37:40','2017-01-04 22:17:06',0,1,NULL,NULL),(2,'member','123456',2,'Tran Ngoc Phuong','chuchuot12a15tnp@gmail.com','0976408409',NULL,NULL,NULL,2,NULL,'xyz1212',NULL,'2016-11-03 19:37:40','2016-12-05 23:53:16',0,0,1234321,'2016-12-06 00:00:00');

/*Table structure for table `t_user_room` */

DROP TABLE IF EXISTS `t_user_room`;

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

/*Data for the table `t_user_room` */

/*Table structure for table `t_userrolegroup` */

DROP TABLE IF EXISTS `t_userrolegroup`;

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

/*Data for the table `t_userrolegroup` */

insert  into `t_userrolegroup`(`userRoleGroupId`,`userId`,`startDay`,`endDay`,`roleGroupId`,`memo`,`created`,`lastUpdate`,`createId`,`updateId`) values (1,1,'2016-11-03','2016-11-03',1,NULL,'2016-11-03 19:40:28','2016-11-03 19:40:28',0,0),(2,2,'2016-11-03','2016-11-03',2,NULL,'2016-11-03 19:40:28','2016-11-03 19:40:28',0,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
