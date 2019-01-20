/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.6.37-log : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `test`;

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` int(11) DEFAULT NULL,
  `perms` char(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `permission` */

insert  into `permission`(`id`,`perms`) values (1,'system:user:select'),(2,'system:user:update'),(3,'system:user:delete'),(4,'system:user:insert'),(5,'system:admin:insert'),(6,'system:admin:update'),(7,'system:admin:select'),(8,'system:admin:delete');

/*Table structure for table `perms_user` */

DROP TABLE IF EXISTS `perms_user`;

CREATE TABLE `perms_user` (
  `user_id` int(11) DEFAULT NULL,
  `perms_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `perms_user` */

insert  into `perms_user`(`user_id`,`perms_id`) values (43,1),(43,2),(43,3),(43,5),(46,3),(49,6),(50,7),(51,8),(53,4),(55,1),(55,7),(57,2),(57,3);

/*Table structure for table `role_user` */

DROP TABLE IF EXISTS `role_user`;

CREATE TABLE `role_user` (
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_user` */

insert  into `role_user`(`user_id`,`role_id`) values (43,2),(44,3),(46,1),(46,2),(46,3),(48,1),(49,1),(50,2),(50,3),(51,3),(52,1),(55,2),(52,2);

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` int(11) DEFAULT NULL,
  `role` char(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `roles` */

insert  into `roles`(`id`,`role`) values (1,'system:admin'),(2,'system:user'),(3,'system:visit');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `gender` char(10) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `password` char(32) DEFAULT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `salt` char(32) DEFAULT NULL COMMENT '盐',
  `email` char(50) DEFAULT NULL,
  `phone` char(11) DEFAULT NULL,
  `brithday` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`gender`,`age`,`password`,`nickname`,`salt`,`email`,`phone`,`brithday`,`create_time`,`modify_time`,`last_login_time`) values (43,'wang0','man',0,'ff1fffdfe71a7586ef1404b52b0681b5',NULL,'1547953988707',NULL,NULL,NULL,'2019-01-20 11:13:09','2019-01-20 11:13:09','2019-01-20 11:13:09'),(44,'wang1','women',1,'9a71b72468cd24088c0aac9df88121f6',NULL,'1547953990713',NULL,NULL,NULL,'2019-01-20 11:13:11','2019-01-20 11:13:11','2019-01-20 11:13:11'),(45,'wang2','man',2,'ca269e3c26fae4914d3a9a4c3ef447aa',NULL,'1547953992713',NULL,NULL,NULL,'2019-01-20 11:13:13','2019-01-20 11:13:13','2019-01-20 11:13:13'),(46,'wang3','women',3,'fb8d4109aabcfeded1f02662ceacf4b6',NULL,'1547953994714',NULL,NULL,NULL,'2019-01-20 11:13:15','2019-01-20 11:13:15','2019-01-20 11:13:15'),(47,'wang4','man',4,'295b41a02c03cf227010237647373b69',NULL,'1547953996714',NULL,NULL,NULL,'2019-01-20 11:13:17','2019-01-20 11:13:17','2019-01-20 11:13:17'),(48,'wang5','women',5,'fb47017e69af37c75b1ab43ba2e7f99c',NULL,'1547953998714',NULL,NULL,NULL,'2019-01-20 11:13:19','2019-01-20 11:13:19','2019-01-20 11:13:19'),(49,'wang6','man',6,'faebe9083fa6d9ed16b4371e401623fb',NULL,'1547954000714',NULL,NULL,NULL,'2019-01-20 11:13:21','2019-01-20 11:13:21','2019-01-20 11:13:21'),(50,'wang7','women',7,'6cf6de50f10ee1cecf68a31a87062bd9',NULL,'1547954002714',NULL,NULL,NULL,'2019-01-20 11:13:23','2019-01-20 11:13:23','2019-01-20 11:13:23'),(51,'wang8','man',8,'8bb645c64feff63f566fcfd17a79c1d9',NULL,'1547954004714',NULL,NULL,NULL,'2019-01-20 11:13:25','2019-01-20 11:13:25','2019-01-20 11:13:25'),(52,'wang9','women',9,'1045b0d136b7211852cfdc9e4760fcfd',NULL,'1547954006714',NULL,NULL,NULL,'2019-01-20 11:13:27','2019-01-20 11:13:27','2019-01-20 11:13:27'),(53,'wang10','man',10,'2497d3476ffd382e21abb17d85f9f5ed',NULL,'1547954008714',NULL,NULL,NULL,'2019-01-20 11:13:29','2019-01-20 11:13:29','2019-01-20 11:13:29'),(54,'wang11','women',11,'3d1b4d3f183355c580ed9e26a7770d1a',NULL,'1547954010715',NULL,NULL,NULL,'2019-01-20 11:13:31','2019-01-20 11:13:31','2019-01-20 11:13:31'),(55,'wang12','man',12,'4ca1ef11c56b145d03db639057105fa5',NULL,'1547954012716',NULL,NULL,NULL,'2019-01-20 11:13:33','2019-01-20 11:13:33','2019-01-20 11:13:33'),(56,'wang13','women',13,'a31f99e21da89f9ea8368fc08b9c5464',NULL,'1547954014716',NULL,NULL,NULL,'2019-01-20 11:13:35','2019-01-20 11:13:35','2019-01-20 11:13:35'),(57,'wang14','man',14,'33c462be78a8772d251823db5a6ed90b',NULL,'1547954016716',NULL,NULL,NULL,'2019-01-20 11:13:37','2019-01-20 11:13:37','2019-01-20 11:13:37');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
