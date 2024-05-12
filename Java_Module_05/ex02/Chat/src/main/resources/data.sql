TRUNCATE TABLE users_chat_rooms, messages, chat_rooms, users RESTART IDENTITY CASCADE;

INSERT INTO users (login, password)
VALUES
    ('user1', 'password1'),
    ('user2', 'password2'),
    ('user3', 'password3'),
    ('user4', 'password4'),
    ('user5', 'password5');


INSERT INTO chat_rooms (name, owner)
VALUES
    ('General', 1),
    ('Technology', 2),
    ('Sports', 3),
    ('Movies', 4),
    ('Random', 5);


INSERT INTO messages (author, text, chat_room)
VALUES
    (1, 'Hello everyone!', 1),
    (2, 'Welcome to the Technology chat room.', 2),
    (3, 'Who''s excited for the big game?', 3),
    (4, 'Has anyone seen the new superhero movie?', 4),
    (5, 'Random thoughts go here.', 5),
    (1, 'How''s everyone doing today?', 1),
    (2, 'Does anyone have experience with Python?', 2),
    (3, 'What''s your favorite sports team?', 3),
    (4, 'I really enjoyed the latest sci-fi movie.', 4),
    (5, 'Isn''t the weather nice today?', 5);


INSERT INTO users_chat_rooms (user_id, chat_room_id)
VALUES
    (1, 1), (1, 2), (1, 3),
    (2, 1), (2, 2),
    (3, 1), (3, 3), (3, 4),
    (4, 1), (4, 4), (4, 5),
    (5, 1), (5, 5);