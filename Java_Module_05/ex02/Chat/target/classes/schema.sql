CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE chat_rooms (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    owner INT NOT NULL,
    FOREIGN KEY (owner) REFERENCES users (id)
);

CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    author INT NOT NULL,
    text TEXT NOT NULL,
    chat_room INT NOT NULL,
    created_at TIMESTAMP(0) DEFAULT NOW(),
    FOREIGN KEY (author) REFERENCES users (id),
    FOREIGN KEY (chat_room) REFERENCES chat_rooms (id)
);