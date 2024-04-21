package fr.school42.chat.repositories;

import fr.school42.chat.models.Message;
import fr.school42.chat.models.Chatroom;
import fr.school42.chat.models.User;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

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
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			statement.setLong(1, id);
			if (resultSet.next()) {
				return Optional.of(createMessageFromResultSet(resultSet));
			} else {
				return Optional.empty();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error finding message by id", e);
		}
	}

	@Override
	public Message save(Message message) {
		String sql = "INSERT INTO messages (author, chat_room, text, created_at) VALUES (?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setLong(1, message.getAuthor().getId());
			statement.setLong(2, message.getChatroom().getId());
			statement.setString(3, message.getText());
			statement.setTimestamp(4, Timestamp.valueOf(message.getDateTime()));
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						message.setId(generatedKeys.getLong(1));
					}
				}
			}
			return message;
		} catch (SQLException e) {
			throw new RuntimeException("Error saving message", e);
		}
	}

	@Override
	public Message update(Message message) {
		String sql = "UPDATE messages SET text = ?, created_at = ? WHERE id = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, message.getText());
			statement.setTimestamp(2, java.sql.Timestamp.valueOf(message.getDateTime()));
			statement.setLong(3, message.getId());
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				return message;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Message createMessageFromResultSet(ResultSet resultSet) throws SQLException {
		Message message = new Message();
		message.setId(resultSet.getLong("id"));
		message.setText(resultSet.getString("text"));
		message.setDateTime(resultSet.getTimestamp("created_at").toLocalDateTime());
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
