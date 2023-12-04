DROP TABLE IF EXISTS events;
CREATE TABLE `events` (
  `event_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `event_start_date` datetime NOT NULL,
  `event_end_date` datetime NOT NULL,
  `location_id` int NOT NULL,
  `amount` int NOT NULL,
  `capacity` int NOT NULL,
  `level_id` int NOT NULL,
  `content` varchar(60) NOT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;