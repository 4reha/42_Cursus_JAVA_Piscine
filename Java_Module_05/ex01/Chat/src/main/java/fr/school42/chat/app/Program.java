package fr.school42.chat.app;

import java.sql.SQLException;
import java.util.Scanner;

import com.zaxxer.hikari.HikariDataSource;

import fr.school42.chat.repositories.MessagesRepository;
import fr.school42.chat.repositories.MessagesRepositoryJdbcImpl;

public class Program {

	private static final String DB_URL = "jdbc:postgresql://localhost:5432/mydb";
	private static final String DB_USERNAME = "myuser";
	private static final String DB_PASSWORD = "mypassword";

	static Long readId() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter a message ID");
		if (!scanner.hasNextLong()) {
			System.exit(1);
		}
		Long id = scanner.nextLong();
		scanner.close();
		return id;
	}

	public static void main(String[] args) throws SQLException {

		try {
			HikariDataSource dataSource = new HikariDataSource();
			dataSource.setJdbcUrl(DB_URL);
			dataSource.setUsername(DB_USERNAME);
			dataSource.setPassword(DB_PASSWORD);

			MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

			final Long id = readId();

			messagesRepository.findById(id).ifPresent(System.out::println);

			dataSource.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
