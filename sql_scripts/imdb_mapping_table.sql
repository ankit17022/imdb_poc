# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.34)
# Database: cms_in
# Generation Time: 2021-08-04 12:51:15 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table imdb_mapping
# ------------------------------------------------------------

DROP TABLE IF EXISTS `imdb_mapping`;

CREATE TABLE `imdb_mapping` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `imdb_title_id` varchar(50) NOT NULL DEFAULT '',
  `cms_content_id` varchar(50) NOT NULL DEFAULT '',
  `score` double NOT NULL DEFAULT '0',
  `payload` json DEFAULT NULL,
  `processed` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `imdb_mapping` WRITE;
/*!40000 ALTER TABLE `imdb_mapping` DISABLE KEYS */;

INSERT INTO `imdb_mapping` (`id`, `imdb_title_id`, `cms_content_id`, `score`, `payload`, `processed`)
VALUES
	(1,'tt0050083','1000007131',1,NULL,1),
	(3,'tt0071853','1000001028',1,NULL,0),
	(55,'tt0064116','123',1,'{"ratings": 8.5, "cast_members": [], "crew_members": [{"job": null, "role": "Frank", "category": "actor", "member_id": "nm0000020", "member_name": "Henry Fonda"}], "number_of_votes": 304363}',1);

/*!40000 ALTER TABLE `imdb_mapping` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
