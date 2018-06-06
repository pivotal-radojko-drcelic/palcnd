DROP DATABASE IF EXISTS demo_dev;
DROP DATABASE IF EXISTS demo_test;

CREATE DATABASE demo_dev;
CREATE DATABASE demo_test;

CREATE USER IF NOT EXISTS 'demouser'@'localhost'
  IDENTIFIED BY '';
GRANT ALL PRIVILEGES ON demo_dev.* TO 'demouser' @'localhost';
GRANT ALL PRIVILEGES ON demo_test.* TO 'demouser' @'localhost';
