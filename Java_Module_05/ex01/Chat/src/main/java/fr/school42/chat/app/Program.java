package fr.school42.chat.app;

import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import fr.school42.chat.repositories.MessagesRepository;
import fr.school42.chat.repositories.MessagesRepositoryJdbcImpl;

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

		messagesRepository.findById(1L).ifPresent(System.out::println);

		dataSource.close();
	}
}
