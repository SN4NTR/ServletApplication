create database if not exists db;
USE db;
CREATE TABLE IF NOT EXISTS users
(
    id         INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(60) NOT NULL
);
CREATE TABLE IF NOT EXISTS cats
(
    id            INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(60) NOT NULL,
    date_of_birth date        not null,
    owner_id      INT         NOT NULL,
    FOREIGN KEY (owner_id) references users (id)
);