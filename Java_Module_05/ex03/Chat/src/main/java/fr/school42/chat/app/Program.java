package fr.school42.chat.app;

import java.sql.SQLException;
import com.zaxxer.hikari.HikariDataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import fr.school42.chat.repositories.MessagesRepository;
import fr.school42.chat.repositories.MessagesRepositoryJdbcImpl;
import fr.school42.chat.models.User;
import fr.school42.chat.models.Chatroom;
import fr.school42.chat.models.Message;

public class Program {

	private static final String DB_URL = "jdbc:postgresql://localhost:5432/mydb";
	private static final String DB_USERNAME = "myuser";
	private static final String DB_PASSWORD = "mypassword";

	public static void main(String[] args) throws SQLException {

		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(DB_URL);
		dataSource.setUsername(DB_USERNAME);
		dataSource.setPassword(DB_PASSWORD);

		MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

		Optional<Message> messageOptional = messagesRepository.findById(11L);

		if (messageOptional.isPresent()) {
			Message message = messageOptional.get();

			message.setText("Hello, World!");
			message.setDateTime(null);

			messagesRepository.update(message);

		} else {
			System.out.println("Message not found");
		}

		dataSource.close();
	}
}
