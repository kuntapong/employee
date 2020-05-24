DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  token VARCHAR(50) NULL,
  token_expire TIMESTAMP NULL
);

INSERT INTO user (username, password) VALUES
  ('admin1', 'password1'),
  ('admin2', 'password2'),
  ('admin3', 'password3');

DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(150) NOT NULL,
  last_name VARCHAR(150) NOT NULL
);