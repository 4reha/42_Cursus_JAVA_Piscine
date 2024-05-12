package fr.school42.chat.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.sql.DataSource;

import fr.school42.chat.models.Chatroom;
import fr.school42.chat.models.Message;
import fr.school42.chat.models.User;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

	private DataSource dataSource;

	public MessagesRepositoryJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Optional<Message> findById(Long id) {
		String sql = "SELECT * FROM messages " +
				"JOIN users u ON messages.author = u.id " +
				"JOIN chat_rooms r ON messages.chat_room = r.id " +
				"WHERE messages.id = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return Optional.of(createMessageFromResultSet(resultSet));
			} else {
				return Optional.empty();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error finding message by id", e);
		}
	}

	private Message createMessageFromResultSet(ResultSet resultSet) throws SQLException {
		Message message = new Message();
		message.setId(resultSet.getLong("id"));
		message.setText(resultSet.getString("text"));
		LocalDateTime dateTime = resultSet.getTimestamp("created_at") == null ? null
				: resultSet.getTimestamp("created_at").toLocalDateTime();
		message.setDateTime(dateTime);
		message.setAuthor(createUserFromResultSet(resultSet));
		message.setChatroom(createChatroomFromResultSet(resultSet));
		return message;
	}

	private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
		User author = new User();
		author.setId(resultSet.getLong("author"));
		author.setLogin(resultSet.getString("login"));
		author.setPassword(resultSet.getString("password"));
		return author;
	}

	private Chatroom createChatroomFromResultSet(ResultSet resultSet) throws SQLException {
		Chatroom chatroom = new Chatroom();
		chatroom.setId(resultSet.getLong("chat_room"));
		chatroom.setName(resultSet.getString("name"));
		return chatroom;
	}
}
