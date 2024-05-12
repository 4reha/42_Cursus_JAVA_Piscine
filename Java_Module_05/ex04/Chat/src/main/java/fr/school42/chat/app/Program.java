package fr.school42.chat.app;

import java.sql.SQLException;
import com.zaxxer.hikari.HikariDataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.school42.chat.repositories.MessagesRepository;
import fr.school42.chat.repositories.MessagesRepositoryJdbcImpl;
import fr.school42.chat.repositories.UserRepository;
import fr.school42.chat.repositories.UserRepositoryJdbcImpl;
import fr.school42.chat.models.User;
import fr.school42.chat.models.Chatroom;
import fr.school42.chat.models.Message;

public class Program {

	private static final String DB_URL = "jdbc:postgresql://localhost:5432/mydb";
	private static final String DB_USERNAME = "myuser";
	private static final String DB_PASSWORD = "mypassword";

	public static void main(String[] args) {

		try (HikariDataSource dataSource = new HikariDataSource()) {
			dataSource.setJdbcUrl(DB_URL);
			dataSource.setUsername(DB_USERNAME);
			dataSource.setPassword(DB_PASSWORD);

			UserRepository userRepository = new UserRepositoryJdbcImpl(dataSource);

			List<User> users = userRepository.findAll(1, 2);

			for (User user : users) {
				System.out.println(user);
			}

			dataSource.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
