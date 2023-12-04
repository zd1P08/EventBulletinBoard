DROP TABLE IF EXISTS users;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `login_id` varchar(20) UNIQUE NOT NULL,
  `login_pass` char(60) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `level_id` int DEFAULT NULL,
  `admin_div` int NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login_id_UNIQUE` (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;