USE tennis_event;

SET AUTOCOMMIT=0;

INSERT INTO levels
 (level_id, level_name)
VALUES
  (1, '初級'),
  (2, '中級'),
  (3, '上級');

INSERT INTO locations
 (location_id, location_name)
VALUES
 (1, '有明テニスの森'),
 (2, '平和の森公園庭球場'),
 (3, 'SENKO潮見テニスコート');

COMMIT;
