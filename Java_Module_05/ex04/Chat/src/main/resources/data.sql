
INSERT INTO
	users (login, password)
values
	('john', 'john123'),
	('mary', 'mary123'),
	('pall', 'pall123'),
	('ruth', 'ruth123'),
	('mike', 'mike123');

INSERT INTO
	chat_rooms (name, owner)
values
	('chat1', 1),
	('chat2', 2),
	('chat3', 3),
	('chat4', 4),
	('chat5', 5);

INSERT INTO
	messages (author, text, chat_room)
values
	(1, 'Hello!', 1),
	(2, 'Hi!', 2),
	(3, 'How are you?', 2),
	(4, 'I am fine, thank you!', 2),
	(5, 'Goodbye!', 1);

INSERT INTO
	users_chat_rooms (user_id, chat_room_id)
values
	(1, 1),
	(2, 2),
	(3, 2),
	(4, 2),
	(5, 1);