DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS chat_rooms;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS users_chat_rooms;

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS chat_rooms (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    owner INT NOT NULL,
    FOREIGN KEY (owner) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS messages (
    id SERIAL PRIMARY KEY,
    author INT NOT NULL,
    text TEXT NOT NULL,
    chat_room INT NOT NULL,
    created_at TIMESTAMP(0) DEFAULT NOW(),
    FOREIGN KEY (author) REFERENCES users (id),
    FOREIGN KEY (chat_room) REFERENCES chat_rooms (id)
);

CREATE TABLE IF NOT EXISTS users_chat_rooms (
    user_id INT NOT NULL,
    chat_room_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (chat_room_id) REFERENCES chat_rooms (id)
);