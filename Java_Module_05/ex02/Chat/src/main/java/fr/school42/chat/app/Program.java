package fr.school42.chat.app;

import java.sql.SQLException;
import com.zaxxer.hikari.HikariDataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

		try {
			HikariDataSource dataSource = new HikariDataSource();
			dataSource.setJdbcUrl(DB_URL);
			dataSource.setUsername(DB_USERNAME);
			dataSource.setPassword(DB_PASSWORD);

			MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

			User creator = new User(1L, "user", "user", new ArrayList<Chatroom>(), new ArrayList<Chatroom>());
			User author = creator;

			Chatroom chatroom = new Chatroom(2L, "room", creator, new ArrayList<Message>());

			Message message = new Message(null, author, chatroom, "Hey, it's beautiful day ain't??", LocalDateTime.now());

			messagesRepository.save(message);

			System.out.println(message.getId());

			dataSource.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
