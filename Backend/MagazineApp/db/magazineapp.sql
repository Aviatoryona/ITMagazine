-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.14-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for magazineapp
CREATE DATABASE IF NOT EXISTS `magazineapp` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `magazineapp`;

-- Dumping structure for table magazineapp.advert
CREATE TABLE IF NOT EXISTS `advert` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `desc` longtext NOT NULL,
  `img` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` int(11) NOT NULL DEFAULT 0,
  `paid` int(11) NOT NULL DEFAULT 0,
  `position` varchar(50) NOT NULL DEFAULT '' COMMENT 'position in magazine',
  `date` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table magazineapp.advert: ~2 rows (approximately)
DELETE FROM `advert`;
/*!40000 ALTER TABLE `advert` DISABLE KEYS */;
INSERT INTO `advert` (`id`, `title`, `desc`, `img`, `user_id`, `status`, `paid`, `position`, `date`) VALUES
	(1, 'Lorem Ipsum', 'LOREM IPSUM GENERATOR\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'default.gif', 2, 1, 0, 'END', '2020-11-30 01:54:01'),
	(2, 'Test', 'Sample advert', '1606720155219EKGJsY.jpg', 8, 0, 0, '', '2020-11-30 10:33:40'),
	(3, 'Meats Online', 'Need some meat?  Holla at johnte@...', '1606721565273WKRhSE.jpg', 9, 0, 0, '', '2020-11-30 10:33:41');
/*!40000 ALTER TABLE `advert` ENABLE KEYS */;

-- Dumping structure for table magazineapp.photos
CREATE TABLE IF NOT EXISTS `photos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `desc` longtext DEFAULT NULL,
  `url` varchar(255) DEFAULT '',
  `user_id` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `date` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table magazineapp.photos: ~3 rows (approximately)
DELETE FROM `photos`;
/*!40000 ALTER TABLE `photos` DISABLE KEYS */;
INSERT INTO `photos` (`id`, `desc`, `url`, `user_id`, `status`, `date`) VALUES
	(1, 'LOREM IPSUM GENERATOR\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '16066487132611P8CFU.jpg', 5, 1, '2020-11-29 14:18:33'),
	(2, 'LOREM IPSUM GENERATOR\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '1606648740085p0FiSf.jpg', 5, 0, '2020-11-29 14:19:00'),
	(3, 'Testing', '1606719217713gdVeRE.jpg', 5, 0, '2020-11-30 09:53:37');
/*!40000 ALTER TABLE `photos` ENABLE KEYS */;

-- Dumping structure for table magazineapp.story
CREATE TABLE IF NOT EXISTS `story` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) NOT NULL DEFAULT 0,
  `paid` int(11) NOT NULL DEFAULT 0,
  `user_id` int(11) NOT NULL,
  `title` text NOT NULL,
  `desc` longtext NOT NULL,
  `photoids` varchar(50) DEFAULT NULL,
  `date` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table magazineapp.story: ~3 rows (approximately)
DELETE FROM `story`;
/*!40000 ALTER TABLE `story` DISABLE KEYS */;
INSERT INTO `story` (`id`, `status`, `paid`, `user_id`, `title`, `desc`, `photoids`, `date`) VALUES
	(1, 1, 1, 1, 'Lorem Ipsum is simply dummy text...', 'lorem ipsum generator\nlorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '1', '2020-11-29 13:37:00'),
	(2, 0, 1, 2, 'Lorem Ipsum', 'LOREM IPSUM GENERATOR\nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'null', '2020-11-29 13:46:55'),
	(3, 1, 0, 3, 'Testing App', 'This is the story details', NULL, '2020-11-30 08:43:11');
/*!40000 ALTER TABLE `story` ENABLE KEYS */;

-- Dumping structure for table magazineapp.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(80) NOT NULL,
  `pwd` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL COMMENT '0 Marketing 1 Editor 2 processing 3 Accounting 4 Advertiser 5 Journalist 6 photographer',
  `date` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Dumping data for table magazineapp.users: ~9 rows (approximately)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `name`, `email`, `pwd`, `category`, `date`) VALUES
	(1, 'Editor', 'editor@test.com', '222222', '1', '2020-11-28 23:51:30'),
	(2, 'Marketer', 'marketer@test.com', '222222', '0', '2020-11-29 13:25:17'),
	(3, 'Journalist', 'journalist@test.com', '222222', '5', '2020-11-30 08:42:07'),
	(4, 'Advertiser', 'advertiser@test.com', '222222', '4', '2020-11-30 08:44:27'),
	(5, 'Photographer ', 'photographer@test.com', '222222', '6', '2020-11-30 09:36:25'),
	(6, 'Processing', 'processing@test.com', '222222', '2', '2020-11-30 09:37:23'),
	(7, 'Accounts', 'accounts@test.com', '222222', '3', '2020-11-30 09:37:43'),
	(8, 'Test', 'test@test.com', '222222', '4', '2020-11-30 10:08:15'),
	(9, 'John', 'johnte@gmail.com', '222222', '4', '2020-11-30 10:31:12');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
