DROP TABLE IF EXISTS levels;
CREATE TABLE `levels` (
  `level_id` int NOT NULL AUTO_INCREMENT,
  `level_name` varchar(20) NOT NULL,
  PRIMARY KEY (`level_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;