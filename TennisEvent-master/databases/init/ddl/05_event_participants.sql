DROP TABLE IF EXISTS event_participants;

CREATE TABLE `event_participants` (
  `event_participant_id` int NOT NULL AUTO_INCREMENT,
  `event_id` int NOT NULL,
  `user_id` int NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`event_participant_id`),
  KEY `fk_event_id_idx` (`event_id`),
  KEY `fk_user_id_idx` (`user_id`),
  CONSTRAINT `fk_participants` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;