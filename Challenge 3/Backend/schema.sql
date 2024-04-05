CREATE DATABASE IF NOT EXISTS loginwebsite;

USE loginwebsite;

CREATE TABLE IF NOT EXISTS sessions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    session_id VARCHAR(255),
    user_id INT,
    username VARCHAR(100),
    started_at TIMESTAMP,
    ended_at DATETIME
);

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255)
);

-- Populate users table
INSERT INTO users (username, password) VALUES ('william_clark', 'password11');
INSERT INTO users (username, password) VALUES ('susan_jones', 'password6');
INSERT INTO users (username, password) VALUES ('sarah_anderson', 'password10');
INSERT INTO users (username, password) VALUES ('robert_harris', 'password15');
INSERT INTO users (username, password) VALUES ('michael_brown', 'password7');
INSERT INTO users (username, password) VALUES ('linda_davis', 'password8');
INSERT INTO users (username, password) VALUES ('laura_roberts', 'password12');
INSERT INTO users (username, password) VALUES ('john_doe', 'password1');
INSERT INTO users (username, password) VALUES ('jennifer_white', 'password14');
INSERT INTO users (username, password) VALUES ('james_thompson', 'password9');
INSERT INTO users (username, password) VALUES ('emily_wilson', 'password4');
INSERT INTO users (username, password) VALUES ('david_miller', 'password5');
INSERT INTO users (username, password) VALUES ('charles_taylor', 'password13');
INSERT INTO users (username, password) VALUES ('bob_jackson', 'password3');
INSERT INTO users (username, password) VALUES ('alice_smith', 'password2');
INSERT INTO users (username, password) VALUES ('1', '1');
