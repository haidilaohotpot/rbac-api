-- MySQL dump 10.13  Distrib 5.7.25, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: rbac
-- ------------------------------------------------------
-- Server version	5.7.25

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
-- Table structure for table `t_permission`
--

DROP TABLE IF EXISTS `t_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限主键',
  `name` varchar(45) NOT NULL COMMENT '权限名称',
  `url` varchar(64) DEFAULT NULL COMMENT '权限地址',
  `icon` varchar(45) DEFAULT NULL COMMENT '权限图标',
  `parent_id` int(11) NOT NULL COMMENT '上一级目录的id',
  `status` int(11) DEFAULT '1' COMMENT '权限状态 0 不可用 1 可用',
  `update_time` date DEFAULT NULL COMMENT '更新时间',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `type` int(11) DEFAULT NULL COMMENT '类型 1 为菜单 2 为 按钮 3为其他',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_permission`
--

LOCK TABLES `t_permission` WRITE;
/*!40000 ALTER TABLE `t_permission` DISABLE KEYS */;
INSERT INTO `t_permission` VALUES (1,'系统管理','#',NULL,0,1,'2019-08-14','2019-08-14',1),(2,'用户管理','/rbac-web/html/user/index.html',NULL,7,1,'2019-08-14','2019-08-14',1),(3,'角色管理','/rbac-web/html/role/index.html',NULL,7,1,'2019-08-14','2019-08-14',1),(4,'权限管理','/rbac-web/html/permission/index.html',NULL,7,1,'2019-08-14','2019-08-14',1),(5,'网站管理','#',NULL,1,1,'2019-08-14','2019-08-14',1),(6,'流量统计','#',NULL,5,1,'2020-06-13','2019-08-14',1),(7,'会员管理','#',NULL,1,1,'2020-06-13','2019-08-14',1);
/*!40000 ALTER TABLE `t_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色主键',
  `name` varchar(45) NOT NULL COMMENT '角色名',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_time` date DEFAULT NULL COMMENT '更新时间',
  `status` int(11) DEFAULT '1' COMMENT '状态 0 禁用 1 可用',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role`
--

LOCK TABLES `t_role` WRITE;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` VALUES (1,'超级管理员','2019-08-14','2019-08-14',1),(2,'普通用户','2019-08-14','2019-08-14',1),(3,'匿名游客','2019-08-14','2020-05-23',1),(4,'管理员','2020-05-24','2020-05-24',1),(5,'学校教师','2020-05-24','2020-06-12',1),(6,'root','2020-06-12','2020-06-12',1),(7,'admin','2020-06-12','2020-06-12',1);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role_permission`
--

DROP TABLE IF EXISTS `t_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8 COMMENT='角色和权限关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role_permission`
--

LOCK TABLES `t_role_permission` WRITE;
/*!40000 ALTER TABLE `t_role_permission` DISABLE KEYS */;
INSERT INTO `t_role_permission` VALUES (39,1,6),(40,1,5),(41,1,2),(42,1,4),(43,1,7),(44,1,1),(74,4,6),(75,4,5),(76,4,2),(77,4,7),(78,4,1),(93,6,2),(94,6,3),(95,6,4),(96,6,7),(97,6,1);
/*!40000 ALTER TABLE `t_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `user_id` varchar(64) NOT NULL COMMENT '用户主键',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `nickname` varchar(64) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `sex` int(11) DEFAULT '2' COMMENT '性别 0 女 1 男 2 保密',
  `face` varchar(2014) DEFAULT NULL COMMENT '头像地址',
  `mobile` varchar(45) DEFAULT NULL COMMENT '手机号',
  `status` int(11) DEFAULT '1' COMMENT '状态 0 禁用 1 可用',
  `update_time` date DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES ('1','root','4QrcOUm6Wau+VuBX8g+IPg==','wonder4work','xiezengchengtx@163.com',2,NULL,'13600966595',1,'2019-08-14','2019-08-14'),('2','wonder4work','4QrcOUm6Wau+VuBX8g+IPg==','POPO','1223@qq.com',1,NULL,'13600966593',1,'2020-05-18','2020-05-18'),('200523ACK15FFC6W','wanglaowu','4QrcOUm6Wau+VuBX8g+IPg==','王老五','123@qq.com',2,NULL,'13600966784',0,'2020-05-23','2020-05-23'),('200523ACZ37HZGXP','wangxiaoer','4QrcOUm6Wau+VuBX8g+IPg==','王老五','123@qq.com',2,NULL,'13600966784',1,'2020-05-23','2020-05-23'),('200523AS0GTACNXP','123','4QrcOUm6Wau+VuBX8g+IPg==','王老五','136000@qq.com',2,NULL,'13600000000',1,'2020-05-23','2020-05-23'),('200523AS12R5X4ZC','1234','4QrcOUm6Wau+VuBX8g+IPg==','王老五','136000@qq.com',2,NULL,'13600000000',0,'2020-05-23','2020-05-23'),('200523AS1DRPW1YW','12345','4QrcOUm6Wau+VuBX8g+IPg==','王老五','136000@qq.com',2,NULL,'13600000000',0,'2020-05-23','2020-05-23'),('200523AS1X9R3P4H','123456','4QrcOUm6Wau+VuBX8g+IPg==','王老五','136000@qq.com',2,NULL,'13600000000',1,'2020-05-23','2020-05-23'),('200523AS29G58MK4','1234567','4QrcOUm6Wau+VuBX8g+IPg==','王老五','136000@qq.com',2,NULL,'13600000000',0,'2020-05-23','2020-05-23'),('200523AS2PT0ZGXP','12345678','4QrcOUm6Wau+VuBX8g+IPg==','王老五','136000@qq.com',2,NULL,'13600000000',0,'2020-05-23','2020-05-23'),('200523AS8W3Y8BR4','yyy','4QrcOUm6Wau+VuBX8g+IPg==','王老五','136000@qq.com',2,NULL,'13600000000',1,'2020-05-23','2020-05-23'),('200523AS9DHCM98H','yyy2222222','83nq88gxsE3hU0adG+w0Xg==','王老五','136000@qq.com',1,NULL,'13600000000',1,'2020-05-23','2020-05-23'),('200523FAZRB5FCH0','uuu','4QrcOUm6Wau+VuBX8g+IPg==','uuu','136000@qq.com',1,NULL,'13600000000',0,'2020-05-23','2020-05-23'),('200612K1P46ZPDP0','小小小王','4QrcOUm6Wau+VuBX8g+IPg==','小小小王','123@qq.com',1,NULL,'13600966593',1,'2020-06-12','2020-06-12');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_role`
--

DROP TABLE IF EXISTS `t_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(64) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_role`
--

LOCK TABLES `t_user_role` WRITE;
/*!40000 ALTER TABLE `t_user_role` DISABLE KEYS */;
INSERT INTO `t_user_role` VALUES (1,'200523AS0GTACNXP',1),(2,'200523AS0GTACNXP',2),(3,'200523AS0GTACNXP',3),(7,'200523AS2PT0ZGXP',2),(8,'200523AS1X9R3P4H',2),(9,'200523AS29G58MK4',2),(10,'200523AS29G58MK4',3),(11,'200523ACZ37HZGXP',2),(13,'200523AS1X9R3P4H',3),(14,'1',1),(15,'1',6);
/*!40000 ALTER TABLE `t_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-13 21:58:23
