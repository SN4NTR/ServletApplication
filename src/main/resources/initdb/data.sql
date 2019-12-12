CREATE DATABASE IF NOT EXISTS db;
USE db;
CREATE TABLE IF NOT EXISTS users
(
    id         INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(60) NOT NULL
);
CREATE TABLE IF NOT EXISTS animals
(
    id            INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(60) NOT NULL,
    date_of_birth DATE        NOT NULL
);
CREATE TABLE IF NOT EXISTS owner_animal
(
    owner_id  INT NULL,
    animal_id INT NULL,
    foreign key (owner_id) references users (id),
    foreign key (animal_id) references animals (id)
);
CREATE TABLE IF NOT EXISTS cats
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    foreign key (id) references animals (id)
);
CREATE TABLE IF NOT EXISTS dogs
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    foreign key (id) references animals (id)
);