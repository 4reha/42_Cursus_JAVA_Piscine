-- Active: 1716378091114@@127.0.0.1@5432@database
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    -- id SERIAL PRIMARY KEY,
    id INTEGER IDENTITY PRIMARY KEY,
    email VARCHAR(50),
    password VARCHAR(50)
);